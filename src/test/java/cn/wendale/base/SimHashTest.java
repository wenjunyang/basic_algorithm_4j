package cn.wendale.base;

import com.google.common.primitives.Chars;

/**
 * Created with IntelliJ IDEA.
 * User: yang.wenjun
 * Date: 2017/5/22
 * Time: 下午2:49
 * Description:
 */
public class SimHashTest {

    private SimHash<Character> simHash = new SimHash<>();

    @org.testng.annotations.Test
    public void testNearest() throws Exception {
    }

    @org.testng.annotations.Test
    public void testCheck() throws Exception {
    }

    @org.testng.annotations.Test
    public void testCheckAndAdd() throws Exception {
    }

    @org.testng.annotations.Test
    public void testFingerprint() throws Exception {
    }

    @org.testng.annotations.Test
    public void testHmDistance() throws Exception {
        String s1 = "只进入身体不打扰生活。能做到加我VX；ggcaowo77";
        String s2 = "只进入身体不打扰生活。能做到加我VX；ga6anx";

        System.out.println(simHash.hmDistance(Chars.asList(s1.toCharArray()), Chars.asList(s2.toCharArray())));
    }

}