package org.example;

public class TestDemoServiceImpl implements DemoService {
    @Override
    public String say(String word) {
        return "from remote say:" + word;
    }
}
