package cn.wendale.base.ac;

import com.google.common.collect.Sets;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: yang.wenjun
 * Date: 2017/5/27
 * Time: 下午4:53
 * Description:
 */
public class AutomatonTest {
    @Test
    public void testFindAll() throws Exception {
        Set<String> keywords = Sets.newHashSet("she", "he", "her", "his", "hers");
        Automaton automaton = new Automaton(keywords);
        List<MatchCase<String>> cases = automaton.findAll("ushers");
        Assert.assertEquals(cases.size(), 4);
    }

}