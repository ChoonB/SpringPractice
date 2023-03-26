package com.example.filetest;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //    테스트용
//    @GetMapping("/")
//    public String test() {
//        return "redirect:/index.html";
//    }

    @PostMapping("/board")
    public String createBoard(BoardRequestDto boardRequestDto) throws IOException {
        return boardService.createBoard(boardRequestDto);
    }

    @GetMapping("/boards")
    public List<Board> getBoardList() {
        return boardService.getBoardList();
    }

    @GetMapping("/board/{id}")
    public String boardone(@PathVariable Long id, Model model) {
        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }
}
