package spectrometers;

import java.util.HashMap;

public interface Spectrometer {

    public boolean connectSpec();

    public HashMap<String,String> getSpecInfo();

    public void setSpec();

    public void runSpec();

    public void closeSpec();

}
