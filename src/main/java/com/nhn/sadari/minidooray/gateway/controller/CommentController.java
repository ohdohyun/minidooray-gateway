package com.nhn.sadari.minidooray.gateway.controller;

import com.nhn.sadari.minidooray.gateway.domain.comment.CommentRegister;
import com.nhn.sadari.minidooray.gateway.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("tasks/{taskId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/register")
    public String getTaskRegisterForm(@PathVariable Long taskId, Model model) {
        model.addAttribute("taskId", taskId);
        model.addAttribute("commentRegister", new CommentRegister());
        return "/comment/comment_register";
    }

    @PostMapping("/register")
    public String doCommentRegister(@PathVariable Long taskId, @ModelAttribute("taskRegister") CommentRegister commentRegister) {

        // #TODO 로그인 구현 이후 id 받아와서 수정
        commentRegister.setWriterId(1L);
        commentService.registerComment(commentRegister, taskId);
        return "/index";
    }

    @GetMapping("/{commentId}/delete")
    public String deleteComment(@PathVariable Long taskId, @PathVariable Long commentId) {
        commentService.deleteComment(taskId, commentId);
        return "/index";
    }
}
