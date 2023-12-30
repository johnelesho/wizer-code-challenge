package tech.elsoft.wizercodechallenge.DTOs.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LongWrapper {

    @NotNull
    @NotEmpty
    private List<Long> ids;
}
