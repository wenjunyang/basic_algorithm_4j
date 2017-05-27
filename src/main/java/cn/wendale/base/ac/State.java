package cn.wendale.base.ac;

import org.testng.collections.Maps;
import org.testng.collections.Sets;

import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: yang.wenjun
 * Date: 2017/5/27
 * Time: 下午3:55
 * Description:
 */


public class State<T, V> {

    private T condition;

    private Set<V> output = Sets.newHashSet();

    private State failState;

    private State preState;

    private Map<T, State<T, V>> gotoMap = Maps.newHashMap();

    public State() {
    }

    public State(State preState) {
        this.preState = preState;
    }

    public State(T condition) {
        this.condition = condition;
    }

    public State(T condition, State preState) {
        this.condition = condition;
        this.preState = preState;
    }

    public Set<V> getOutput() {
        return output;
    }

    public void setOutput(Set<V> output) {
        this.output = output;
    }

    public State getFailState() {
        return failState;
    }

    public void setFailState(State failState) {
        this.failState = failState;
    }

    public State getPreState() {
        return preState;
    }

    public void setPreState(State preState) {
        this.preState = preState;
    }

    public Map<T, State<T, V>> getGotoMap() {
        return gotoMap;
    }

    public void setGotoMap(Map<T, State<T, V>> gotoMap) {
        this.gotoMap = gotoMap;
    }

    public T getCondition() {
        return condition;
    }

    public void setCondition(T condition) {
        this.condition = condition;
    }

    public State<T, V> gotoStae(T c) {
        return gotoMap.get(c);
    }

    public static <T, V> State<T, V> initState() {
        State<T, V> state = new State<>();
        state.setFailState(null);
        state.setPreState(state);
        return state;
    }
}
