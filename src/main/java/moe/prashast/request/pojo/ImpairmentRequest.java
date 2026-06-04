package moe.prashast.request.pojo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class ImpairmentRequest {

    @NotEmpty(message = "Impairment IDs are required")
    private List<Integer> impairmentIds;
}
