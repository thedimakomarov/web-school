package com.komarov.webschool.dto;

import com.komarov.webschool.entity.Group;
import com.komarov.webschool.utility.StringUtility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InnerGroupDto {
    @Null(message = "should be null")
    private Long id;

    @NotBlank(message = "should be not empty and not null")
    private String name;

    public static InnerGroupDto parse(Group group) {
        if(group == null) {
            return new InnerGroupDto();
        }
        return new InnerGroupDto(
                group.getId(),
                StringUtility.makeFirstNotNullCharUpperCase(group.getName())
        );
    }

    public static List<InnerGroupDto> parse(List<Group> groups) {
        return groups.stream()
                .map(InnerGroupDto::parse)
                .toList();
    }
}
