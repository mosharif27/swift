package com.SwiftCaseChallenge.Swift;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SwiftCaseService {

    // Injecting values from properties
    @Value("${swiftcase.api.key}")
    private String apiKey;

    @Value("${swiftcase.subdomain}")
    private String subdomain;

    // Base URL for SwiftCase API
    private final String baseUrl = "https://" + subdomain + ".swiftcase.co.uk/api/v2/";

    // RestTemplate for making HTTP requests
    private final RestTemplate restTemplate;

    // Constructor injection of RestTemplate
    public SwiftCaseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Retrieve task IDs for a given workflow status
    public List<Integer> getTaskIdsForWorkflowStatus(int workflowStatusId) {
        String url = baseUrl + "status/" + workflowStatusId + ".json";
        try {
            TaskIdCollection response = restTemplate.getForObject(url, TaskIdCollection.class);
            if (response != null && response.getTaskIds() != null) {
                return response.getTaskIds().stream()
                        .map(TaskIdWrapper::getId)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            // Log the exception for further analysis
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve details of a task
    public Task getTaskDetails(int taskId) {
        String url = baseUrl + "task/" + taskId + ".json";
        try {
            TaskDetailsResponse response = restTemplate.getForObject(url, TaskDetailsResponse.class);
            return response != null ? response.getTask() : null;
        } catch (Exception e) {
            // Log the exception for further analysis
            e.printStackTrace();
        }
        return null;
    }

    // Requirement 13: Upload a file to a task API endpoint
    public void uploadFileToTask(int taskId, MultipartFile file) {
        String url = baseUrl + "task/" + taskId + "/upload-file";

        // Set headers for the HTTP request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Set the body of the HTTP request with the file
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(convert(file)));

        // Create the HTTP entity with headers and body
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            // Make the HTTP request to upload the file
            restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        } catch (Exception e) {
            // Log the exception for further analysis
            e.printStackTrace();
            // Throw a runtime exception if there's an error uploading the file
            throw new RuntimeException("Error uploading file to task.");
        }
    }

    // Helper method to convert MultipartFile to File
    private File convert(MultipartFile file) {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            file.transferTo(convertedFile);
        } catch (Exception e) {
            // Log the exception for further analysis
            e.printStackTrace();
            // Throw a runtime exception if there's an error converting the file
            throw new RuntimeException("Error converting file.");
        }
        return convertedFile;
    }
}
