package cn.wendale.base.ac;

import com.google.common.base.Preconditions;
import com.google.common.collect.Queues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.collections.Lists;

import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: yang.wenjun
 * Date: 2017/5/27
 * Time: 下午3:51
 * Description:
 */


public class Automaton {

    private static final Logger LOGGER = LoggerFactory.getLogger(Automaton.class);

    private Set<String> keywords;

    private State<Character, String> initState;

    public Automaton(Set<String> keywords) {
        for (String keyword : keywords) {
            Preconditions.checkArgument(keyword != null && keyword.length() > 0,
                    "keyword不能为空");
        }
        this.keywords = keywords;
        build();
    }

    private void build() {
        initState = State.initState();

        // 建立goto函数及部分output
        for (String keyword : keywords) {
            State<Character, String> currentState = initState;
            for (int i = 0; i < keyword.length(); i++) {
                Character c = keyword.charAt(i);
                if (currentState.getGotoMap().containsKey(c)) {
                    currentState = currentState.getGotoMap().get(c);
                } else {
                    State<Character, String> newState = new State<>(c, currentState);
                    currentState.getGotoMap().put(c, newState);
                    currentState = newState;
                }
                if (i == keyword.length() - 1) {
                    currentState.getOutput().add(keyword);
                }
            }
        }

        // 建立fail函数及完善output函数
        Queue<State<Character, String>> toBuildList = Queues.newArrayDeque();
        toBuildList.add(initState);
        while (!toBuildList.isEmpty()) {
            State<Character, String> state = toBuildList.poll();
            for (State<Character, String> layerState : state.getGotoMap().values()) {
                if (state == initState) {
                    layerState.setFailState(initState);
                } else {
                    State<Character, String> preState = layerState.getPreState();
                    State<Character, String> failState = preState.getFailState();
                    while (failState != initState
                            && !failState.getGotoMap().containsKey(layerState.getCondition())) {
                        failState = failState.getFailState();
                    }
                    if (failState.getGotoMap().containsKey(layerState.getCondition())) {
                        layerState.setFailState(failState.gotoStae(layerState.getCondition()));
                        layerState.getOutput().addAll(failState.gotoStae(layerState.getCondition()).getOutput());
                    } else {
                        layerState.setFailState(failState);
                    }

                }
                toBuildList.add(layerState);
            }

        }
    }

    public List<MatchCase<String>> findAll(String text) {
        List<MatchCase<String>> result = Lists.newArrayList();
        State<Character, String> currentState = initState;
        int i = 0;
        while (i < text.length()) {
            Character c = text.charAt(i);
            if (currentState.getGotoMap().containsKey(c)) {
                currentState = currentState.gotoStae(c);
                for (String s : currentState.getOutput()) {
                    MatchCase<String> matchCase = new MatchCase<>(i - s.length() + 1, i, s);
                    result.add(matchCase);
                }
            } else {
                if (currentState != initState) {
                    currentState = currentState.getFailState();
                    continue;
                }
            }
            i++;
        }
        return result;
    }
}
