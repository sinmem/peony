package com.sinmem.peony.common.utils;

import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Random;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.common.utils
 * @Author sinmem
 * @CreateTime 2019-12-17 16:00
 * @Description 随机码生成工具
 */
public class RandomUtils {
    private static final Logger LOG = LoggerFactory.getLogger(RandomUtils.class);
    private static RandomUtils randomUtils;
    private final int ARR_LENGTH = 32;
    private final int CHARS_SIZE = 6;

    private final char[] arr1 = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private char[][] chars = new char[CHARS_SIZE][ARR_LENGTH];
    private Random r;
    static {
        if(randomUtils == null){
            randomUtils = new RandomUtils();
        }
    }
    public static RandomUtils getInstance(){
        return randomUtils;
    }

    public RandomUtils(){
        init();
    }

    /**
     * 初始化随机字符数组,用于获取验证字符
     */
    public void init(){
        File file = new File("randomCode.json");
        if(!file.exists()){
            LOG.error("warning: randomCode.json doesn't exists! ");
            LOG.warn("Will be general randomCode.json! 这将影响到邀请码的唯一性,导致出现重复邀请码!");
        }else {
            read(chars);
        }
    }

    private void read(char[][] codeArrays){
        try(BufferedReader fw = new BufferedReader(new FileReader("randomCode.json"));
        ){
            String jsonStr = fw.readLine();
            System.out.println(jsonStr);
            chars = GsonUtils.fromJson(jsonStr, chars.getClass());
            System.out.println("Arrays of randomCode.json:");
            System.out.println(Arrays.toString(chars));
        }catch (IOException e){
            LOG.error("随机码文件访问失败", e.getCause());
        }
    }

    private void write(char[][] codeArrays){
        try(FileWriter fw = new FileWriter(new File("randomCode.json"));
        ){
            for (int i = 0; i < CHARS_SIZE; i++) {
                shuffle(arr1);
                System.arraycopy(arr1, 0, chars[i], 0, ARR_LENGTH);
            }
            fw.write(GsonUtils.toJson(chars));
        }catch (IOException e){
            LOG.error("写入随机码文件失败", e.getCause());
        }
    }

    private void shuffle(@NotNull char[] arr){
        Random rnd = r;
        if (rnd == null)
            r = rnd = new Random(); // harmless race.
        shuffle(arr, rnd);
    }

    private void shuffle(@NotNull char[] arr, @NotNull Random rnd){
        int size = arr.length;
        // Shuffle array
        for (int i=size; i>1; i--)
            swap(arr, i-1, rnd.nextInt(i));
    }

    /**
     * Swaps the two specified elements in the specified array.
     */
    private static void swap(char[] arr, int i, int j) {
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * 根据传入的值生成指定格式的验证码
     * @param id
     * @param codeLength
     * @return
     */
    public String getRandomCode(Long id, @NotNull int codeLength){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            // 获取id的低五位, 并右移
            int charIndex = id.intValue() & 0b11111;
            sb.append(chars[i][charIndex]);
            id = id >>> 5;
        }
        return sb.toString();
    }

    public String getNumRandomCode(){
        Random rnd = r;
        if (rnd == null)
            r = rnd = new Random(); // harmless race.
        return String.valueOf(rnd.nextInt(899999)+100000);
    }

    public String getNumRandomCode(Random rnd){
        rnd = new Random(); // harmless race.
        return String.valueOf(rnd.nextInt(899999)+100000);
    }

}
