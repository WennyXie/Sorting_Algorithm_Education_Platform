package com.example.sorting_algorithm_education_platform;

import com.example.sorting_algorithm_education_platform.controller.BubbleSortController;
import com.example.sorting_algorithm_education_platform.entity.BubbleSort;
import com.example.sorting_algorithm_education_platform.mapper.BubbleSortMapper;
import com.example.sorting_algorithm_education_platform.util.Res;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class BubbleSortControllerTest {

    @Mock
    private BubbleSortMapper bubbleSortMapper;

    @InjectMocks
    private BubbleSortController bubbleSortController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindSortById() {
        BubbleSort mockBubbleSort = new BubbleSort();
        mockBubbleSort.setBubbleId(1);
        mockBubbleSort.setExchange(0);
        mockBubbleSort.setPrePos(0);
        mockBubbleSort.setPostPos(0);
        mockBubbleSort.setCurrList("1, 6, 5, 3");
        mockBubbleSort.setPracticeId(1);
        mockBubbleSort.setProcessNum(0);
        mockBubbleSort.setUserId(1);
        mockBubbleSort.setTurn(0);
        when(bubbleSortMapper.selectById(1)).thenReturn(mockBubbleSort);

        ResponseEntity
                <Res<BubbleSort>> response = bubbleSortController.findSortById("mockToken", 1);

        assertNotNull(response);
        assertEquals(1, response.getBody().getCode());
        assertEquals("success", response.getBody().getMsg());
        assertEquals(mockBubbleSort, response.getBody().getData());

        verify(bubbleSortMapper, times(1)).selectById(1);
    }

    @Test
    public void testFindNextStep() {
        BubbleSort mockBubbleSort1 = new BubbleSort();
        mockBubbleSort1.setBubbleId(1);
        mockBubbleSort1.setExchange(0);
        mockBubbleSort1.setPrePos(0);
        mockBubbleSort1.setPostPos(0);
        mockBubbleSort1.setCurrList("1, 6, 5, 3");
        mockBubbleSort1.setPracticeId(1);
        mockBubbleSort1.setProcessNum(0);
        mockBubbleSort1.setUserId(1);
        mockBubbleSort1.setTurn(0);

        BubbleSort mockBubbleSort2 = new BubbleSort();
        mockBubbleSort2.setBubbleId(2);
        mockBubbleSort2.setExchange(0);
        mockBubbleSort2.setPrePos(0);
        mockBubbleSort2.setPostPos(0);
        mockBubbleSort2.setCurrList("1, 6, 5, 3");
        mockBubbleSort2.setPracticeId(1);
        mockBubbleSort2.setProcessNum(1);
        mockBubbleSort2.setUserId(1);
        mockBubbleSort2.setTurn(1);

        when(bubbleSortMapper.findNextStep(1, 0)).thenReturn(mockBubbleSort2);

        ResponseEntity
                <Res<BubbleSort>> response = bubbleSortController.findNextStep("mockToken", 1, 0);

        assertNotNull(response);
        assertEquals(1, response.getBody().getCode());
        assertEquals("success", response.getBody().getMsg());
        assertEquals(mockBubbleSort2, response.getBody().getData());

        verify(bubbleSortMapper, times(1)).findNextStep(1, 0);
    }

    @Test
    public void testFindCurrList() {
        BubbleSort mockBubbleSort = new BubbleSort();
        mockBubbleSort.setBubbleId(1);
        mockBubbleSort.setExchange(0);
        mockBubbleSort.setPrePos(0);
        mockBubbleSort.setPostPos(0);
        mockBubbleSort.setCurrList("1, 6, 5, 3");
        mockBubbleSort.setPracticeId(1);
        mockBubbleSort.setProcessNum(0);
        mockBubbleSort.setUserId(1);
        mockBubbleSort.setTurn(0);
        when(bubbleSortMapper.findCurrList(1, 0)).thenReturn(mockBubbleSort.getCurrList());
        ResponseEntity
                <Res<String>> response = bubbleSortController.findCurrList("mockToken", 1, 0);

        assertNotNull(response);
        assertEquals(1, response.getBody().getCode());
        assertEquals("success", response.getBody().getMsg());
        assertEquals("1, 6, 5, 3", response.getBody().getData());

        verify(bubbleSortMapper, times(1)).findCurrList(1, 0);
    }

    @Test
    public void testFindSolution() {
        BubbleSort mockBubbleSort1 = new BubbleSort();
        mockBubbleSort1.setBubbleId(1);
        mockBubbleSort1.setExchange(0);
        mockBubbleSort1.setPrePos(0);
        mockBubbleSort1.setPostPos(0);
        mockBubbleSort1.setCurrList("5, 1, 3");
        mockBubbleSort1.setPracticeId(3);
        mockBubbleSort1.setProcessNum(0);
        mockBubbleSort1.setUserId(2);
        mockBubbleSort1.setTurn(0);

        BubbleSort mockBubbleSort2 = new BubbleSort();
        mockBubbleSort2.setBubbleId(2);
        mockBubbleSort2.setExchange(1);
        mockBubbleSort2.setPrePos(0);
        mockBubbleSort2.setPostPos(1);
        mockBubbleSort2.setCurrList("1, 5, 3");
        mockBubbleSort2.setPracticeId(3);
        mockBubbleSort2.setProcessNum(1);
        mockBubbleSort2.setUserId(2);
        mockBubbleSort2.setTurn(1);

        BubbleSort mockBubbleSort3 = new BubbleSort();
        mockBubbleSort3.setBubbleId(3);
        mockBubbleSort3.setExchange(1);
        mockBubbleSort3.setPrePos(1);
        mockBubbleSort3.setPostPos(2);
        mockBubbleSort3.setCurrList("1, 3, 5");
        mockBubbleSort3.setPracticeId(3);
        mockBubbleSort3.setProcessNum(2);
        mockBubbleSort3.setUserId(2);
        mockBubbleSort3.setTurn(1);

        BubbleSort mockBubbleSort = new BubbleSort();
        mockBubbleSort.setBubbleId(3);
        mockBubbleSort.setExchange(0);
        mockBubbleSort.setPrePos(0);
        mockBubbleSort.setPostPos(0);
        mockBubbleSort.setCurrList("1, 3, 5");
        mockBubbleSort.setPracticeId(3);
        mockBubbleSort.setProcessNum(3);
        mockBubbleSort.setUserId(2);
        mockBubbleSort.setTurn(2);

        when(bubbleSortMapper.findSolution(3)).thenReturn(mockBubbleSort.getCurrList());

        ResponseEntity
                <Res<String>> response = bubbleSortController.findSolution("mockToken", 3);

        assertNotNull(response);
        assertEquals(1, response.getBody().getCode());
        assertEquals("success", response.getBody().getMsg());
        assertEquals(mockBubbleSort.getCurrList(), response.getBody().getData());

        verify(bubbleSortMapper, times(1)).findSolution(3);
    }
}