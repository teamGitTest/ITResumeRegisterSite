package kr.co.itresumeregistersite.domain.dto.boardsDto;

import lombok.Getter;

@Getter
public class EditPostDto {

    private Long boardId;

    private String title;

    private String content;
}