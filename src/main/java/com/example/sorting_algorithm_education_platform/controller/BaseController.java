package com.example.sorting_algorithm_education_platform.controller;

import com.example.sorting_algorithm_education_platform.entity.BubbleSort;
import com.example.sorting_algorithm_education_platform.service.BubbleSortService;
import com.example.sorting_algorithm_education_platform.service.UserService;
import com.example.sorting_algorithm_education_platform.util.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/sort")
public class BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private BubbleSortService bubbleSortService;

    @PostMapping("/add")
    public ResponseEntity<Res<String>> addSort(@RequestHeader("token") String token,
                                               @RequestParam(value = "sortList") String sortList,
                                               @RequestParam(value = "practiceId") Integer practiceId,
                                               @RequestParam(value = "userId") Integer userId) {

        if (!userService.userIdExists(userId)) {
            System.out.println("user id not exist");
            return ResponseEntity.badRequest().body(new Res<>(0, "用户ID不存在", null));
        }

        if (practiceIdExistsForUser(userId, practiceId)) {
            System.out.println("user id && practiceId not exist");
            return ResponseEntity.badRequest().body(new Res<>(0, "题号存在", null));
        }
        if (!isValidSequence(sortList)) {
            System.out.println("invalid sequence");
            return ResponseEntity.badRequest().body(new Res<>(0, "序列无效", null));
        }
        try {
            // insert
            BubbleSort bubbleSort = new BubbleSort();
            // String currList = sortList.toString().replace("[", "").replace("]", "");
            bubbleSort.setCurrList(sortList);
            bubbleSort.setPracticeId(practiceId);
            bubbleSort.setProcessNum(0);
            bubbleSort.setUserId(userId);
            bubbleSort.setExchange(0);
            bubbleSort.setPrePos(0);
            bubbleSort.setPostPos(0);
            bubbleSort.setTurn(0);

            bubbleSortService.insertSort(bubbleSort);
            System.out.println("------------------------");
            recordBubbleSortSteps(sortList,practiceId, userId);

            return ResponseEntity.ok(new Res<>(1, "添加成功",null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Res<>(0, "添加失败: " , e.getMessage()));
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<Res<String>> deleteSort(@RequestHeader("token") String token,
                                                  @RequestParam("practiceId") Integer practiceId,
                                                  @RequestParam("userId") Integer userId) {
        if (!userService.userIdExists(userId)) {
            return ResponseEntity.badRequest().body(new Res<>(0, "用户ID不存在", null));
        }

        if (!practiceIdExistsForUser(userId, practiceId)) {
            return ResponseEntity.badRequest().body(new Res<>(0, "题号不存在", null));
        }
        try {
            bubbleSortService.deleteSort(practiceId, userId);
            return ResponseEntity.ok(new Res<>(1, "删除成功",null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Res<>(0,"删除失败: ",e.getMessage()));
        }
    }

    @PostMapping("/modify")
    public ResponseEntity<Res<String>> modifySort(@RequestHeader("token") String token,
                                                  @RequestParam(value = "sortList") String sortList,
                                                  @RequestParam(value = "practiceId") Integer practiceId,
                                                  @RequestParam(value = "userId") Integer userId) {
        if (!userService.userIdExists(userId)) {
            return ResponseEntity.badRequest().body(new Res<>(0, "用户ID不存在", null));
        }

        if (!practiceIdExistsForUser(userId, practiceId)) {
            return ResponseEntity.badRequest().body(new Res<>(0, "题号不存在", null));
        }
        if (!isValidSequence(sortList)) {
            return ResponseEntity.badRequest().body(new Res<>(0, "序列无效", null));
        }
        try {
            //first delete
            bubbleSortService.deleteSort(practiceId, userId);
            // insert
            BubbleSort bubbleSort = new BubbleSort();
            // String currList = sortList.toString().replace("[", "").replace("]", "");
            bubbleSort.setCurrList(sortList);
            bubbleSort.setPracticeId(practiceId);
            bubbleSort.setProcessNum(0);
            bubbleSort.setUserId(userId);
            bubbleSort.setExchange(0);
            bubbleSort.setPrePos(0);
            bubbleSort.setPostPos(0);
            bubbleSort.setTurn(0);
            bubbleSortService.insertSort(bubbleSort);
            recordBubbleSortSteps(sortList,practiceId, userId);
            return ResponseEntity.ok(new Res<>(1, "添加成功",null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Res<>(0,"删除失败: ",e.getMessage()));
        }
    }

    // 方法用于检查该userID的practiceId是否存在
    private boolean practiceIdExistsForUser(Integer userId, Integer practiceId) {
        int count = bubbleSortService.countByPracticeIdAndUserId(practiceId, userId);
        return count > 0;
    }

    // 方法用于检查传进来的序列是否正确
    public boolean isValidSequence(String sequence) {
        String regex = "^\\d+(\\s*,\\s*\\d+)*$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(sequence).matches();
    }

    public void recordBubbleSortSteps(String inputList, int practiceId, int userId) {
        int processNum = 0;
        int turn = 0;
        int prePos = 0;
        int postPos = 0;
        int exchange = 0;
        String currList = null;

        String[] stringArray = inputList.split(","); // 使用逗号分割字符串，得到字符串数组
        List<Integer> integerList = new ArrayList<>();
        for (String str : stringArray) {
            integerList.add(Integer.parseInt(str.trim()));
        }


        int n = integerList.size();
        processNum = 0;
        for (int i = 0; i < n - 1; i++) {
            turn++;
            exchange = 0;
            prePos = 0;
            postPos = 0;


            for (int j = 0; j < n - i - 1; j++) {
                if (integerList.get(j) > integerList.get(j + 1)) {
                    exchange = 1;
                    prePos = j;
                    postPos = j + 1;
                    int temp = integerList.get(j);
                    integerList.set(j, integerList.get(j + 1));
                    integerList.set(j + 1, temp);
                    processNum++;
                    currList = integerList.toString().replace("[", "").replace("]", "");
                    BubbleSort bubbleSort = new BubbleSort();
                    bubbleSort.setPracticeId(practiceId);
                    bubbleSort.setUserId(userId);
                    bubbleSort.setTurn(turn);
                    bubbleSort.setExchange(exchange);
                    bubbleSort.setPrePos(prePos);
                    bubbleSort.setPostPos(postPos);
                    bubbleSort.setCurrList(currList);
                    bubbleSort.setProcessNum(processNum);
                    System.out.println("change " + turn + " " + processNum+ " "+ prePos +" "+ postPos);
                    bubbleSortService.insertSort(bubbleSort);
                }

            }
            if(exchange == 0) {
                processNum++;
                currList = integerList.toString().replace("[", "").replace("]", "");
                BubbleSort bubbleSort = new BubbleSort();
                bubbleSort.setPracticeId(practiceId);
                bubbleSort.setUserId(userId);
                bubbleSort.setTurn(turn);
                bubbleSort.setExchange(exchange);
                bubbleSort.setPrePos(prePos);
                bubbleSort.setPostPos(postPos);
                bubbleSort.setCurrList(currList);
                bubbleSort.setProcessNum(processNum);
                System.out.println("no " + turn + " " + processNum);
                bubbleSortService.insertSort(bubbleSort);
            }
        }
    }
}
