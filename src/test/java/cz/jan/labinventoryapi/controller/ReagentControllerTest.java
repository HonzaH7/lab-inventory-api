package cz.jan.labinventoryapi.controller;

import cz.jan.labinventoryapi.exception.ReagentNotFoundException;
import cz.jan.labinventoryapi.model.Category;
import cz.jan.labinventoryapi.model.Reagent;
import cz.jan.labinventoryapi.model.Unit;
import cz.jan.labinventoryapi.service.ReagentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReagentController.class)
class ReagentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReagentService service;

    @Test
    void getById_returnsReagent_whenFound() throws Exception {
        Reagent reagent = new Reagent(1L, "Kyselina sírová", Category.ACID, 500, Unit.ML,
                LocalDate.of(2027, 1, 1));
        given(service.getById(1L)).willReturn(reagent);

        mockMvc.perform(get("/reagents/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Kyselina sírová"));
    }

    @Test
    void getById_returns404_whenNotFound() throws Exception {
        given(service.getById(99L)).willThrow(new ReagentNotFoundException(99L));

        mockMvc.perform(get("/reagents/99"))
                .andExpect(status().isNotFound());
    }
}