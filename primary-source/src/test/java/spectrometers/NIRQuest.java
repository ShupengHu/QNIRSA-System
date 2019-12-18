package spectrometers;

import java.util.HashMap;

public class NIRQuest implements Spectrometer{
    @Override
    public boolean connectSpec() {

        return false;
    }

    @Override
    public HashMap<String, String> getSpecInfo() {
        return null;
    }

    @Override
    public void setSpec() {

    }


    public void setInput() {

    }

    @Override
    public void runSpec() {

    }

    @Override
    public void closeSpec() {

    }
}
