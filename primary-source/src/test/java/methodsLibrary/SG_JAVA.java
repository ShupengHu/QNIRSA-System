package methodsLibrary;

import MSC_MATLAB.MSCMATLAB;
import SG_MATLAB.SGMATLAB;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWComplexity;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

public class SG_JAVA implements Method{
    private Object[] result;
    private MWNumericArray matrix;
    private MWNumericArray Wavelength;
    private double[][] spectrabySG;
    private int window_length;
    private int polynomial_order;
    private int derivative_order;

    public void setParameter(int window_length, int polynomial_order, int derivative_order){
        this.window_length=window_length;
        this.polynomial_order=polynomial_order;
        this.derivative_order=derivative_order;
    }

    public void  setWavelength(double[][] wavelength){
        /*------convert a double array into a matrix*/
        //set size of matrix
        int[] n={wavelength.length,wavelength[0].length};
        Wavelength=MWNumericArray.newInstance(n, MWClassID.DOUBLE, MWComplexity.REAL);
        /* Set matrix values */
        int[] index = {1, 1};
        for (index[0]= 1; index[0] <= n[0]; index[0]++) {
            for (index[1] = 1; index[1] <= n[1]; index[1]++) {
                //index of the first element in MATLAB is（1,1), but index of two-dimensional double array in Java is [0,0]
                Wavelength.set(index,wavelength[index[0]-1][index[1]-1]);
            }
        }
    }

    @Override
    public void invokeMethod(double[][] data) {
        /*------convert a double array into a matrix*/
        //set size of matrix
        int[] n={data.length,data[0].length};
        matrix=MWNumericArray.newInstance(n, MWClassID.DOUBLE, MWComplexity.REAL);
        /* Set matrix values */
        int[] index = {1, 1};
        for (index[0]= 1; index[0] <= n[0]; index[0]++) {
            for (index[1] = 1; index[1] <= n[1]; index[1]++) {
                //index of the first element in MATLAB is（1,1), but index of two-dimensional double array in Java is [0,0]
                matrix.set(index,data[index[0]-1][index[1]-1]);
            }
        }

        //invoke MATLAB method
        try {
            SGMATLAB sgmatlab=new SGMATLAB();
            //the first integer parameter means how many objects will return in result
            result=sgmatlab.SG(1, matrix,Wavelength,derivative_order,polynomial_order,window_length);
        } catch (MWException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parseResult() {
        spectrabySG=(double[][]) ((MWNumericArray)result[0]).toDoubleArray();
    }

    @Override
    public double[][] getResult() {
        return spectrabySG;
    }
}
