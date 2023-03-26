package com.example.filetest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;


    public String createBoard(BoardRequestDto boardRequestDto) throws IOException {
        MultipartFile file = boardRequestDto.getFile();
//      현재프로젝트 경로 + static아래 files 폴더
        String projectPath = System.getProperty("user.dir") +"\\src\\main\\webapp";
//        "랜덤값_원래파일명"으로 저장
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);

        Board board = new Board(boardRequestDto);

        board.setFilename(fileName);
        board.setFilepath("/webapp/" + fileName);

        boardRepository.save(board);
        return "저장 성공";
    }


    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }

    public Object boardView(Long id) {
        return boardRepository.findById(id);
    }
}
