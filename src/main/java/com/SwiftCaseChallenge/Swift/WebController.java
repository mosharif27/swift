package com.SwiftCaseChallenge.Swift;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    private final SwiftCaseService swiftCaseService;

    @Autowired
    public WebController(SwiftCaseService swiftCaseService) {
        this.swiftCaseService = swiftCaseService;
    }

    // Display the main form
    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("productStatusId", "");
        return "index";
    }

    // Handle form submission
    @PostMapping("/processForm")
    public String processForm(@RequestParam("productStatusId") String productStatusId, Model model) {
        try {
            // Requirement 2: Validate that the Product Status ID is entered
            if (productStatusId.isEmpty()) {
                model.addAttribute("error", "Product Status ID is required.");
                return "index";
            }

            // Requirement 3: Validate that the Product Status ID is an integer
            int workflowStatusId = Integer.parseInt(productStatusId);

            // Requirement 6: Get task IDs
            List<Integer> taskIds = swiftCaseService.getTaskIdsForWorkflowStatus(workflowStatusId);

            // Requirement 7: Get details of each task
            List<Task> taskDetailsList = new ArrayList<>();
            if (taskIds != null) {
                for (int taskId : taskIds) {
                    Task taskDetailsResponse = swiftCaseService.getTaskDetails(taskId);
                    taskDetailsList.add(taskDetailsResponse);
                    // Process task details as needed
                }
            }

            // Requirement 9: Sum the Cost data for each Task where Cancelled = "No" in the results.
            double totalCost = taskDetailsList.stream()
                    .filter(task -> !task.isCancelled())
                    .mapToDouble(Task::getCost)
                    .sum();

            // Requirement 10: Format the calculated answer
            String formattedCost = String.format("£%.2f", totalCost);


            model.addAttribute("tasks", taskDetailsList);
            model.addAttribute("totalCost", formattedCost);


            //works practically as n else if an integer isnt entered
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Product Status ID must be an integer.");
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while processing your request.");
            // Log the exception for further analysis
            e.printStackTrace();
        }

        return "index";
    }

    // Requirement 13: Handle file upload
    @PostMapping("/uploadFile")
    public String handleFileUpload(@RequestParam("taskId") int taskId, @RequestParam("file") MultipartFile file, Model model) {
        try {
            swiftCaseService.uploadFileToTask(taskId, file);
            model.addAttribute("success", "File uploaded successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Error uploading file. Please try again.");
            // Log the exception for further analysis
            e.printStackTrace();
        }
        return "redirect:/";
    }


}
