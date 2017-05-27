package cn.wendale.base.ac;

import com.google.common.collect.Sets;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: yang.wenjun
 * Date: 2017/5/27
 * Time: 下午5:44
 * Description:
 */


public class AutomatonInteractiveText {

    public static void main(String[] args) {
        System.out.println("输入keywords，空格分割");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        Set<String> keywords = Sets.newHashSet();
        for (String s : line.split("\\s")) {
            keywords.add(s);
        }
        Automaton automaton = new Automaton(keywords);
        while (true) {
            System.out.println("输入待匹配字符串：");
            List<MatchCase<String>> result = automaton.findAll(scanner.nextLine());
            System.out.println(String.format("匹配个数：%d,结果如下", result.size()));
            for (MatchCase<String> matchCase : result) {
                System.out.println(String.format("start: %d, end: %d, keyword: %s",
                        matchCase.getStart(), matchCase.getEnd(), matchCase.getKeyword()));
            }
        }
    }
}
