package methodsLibrary;


import SNV_MATLAB.SNVMATLAB;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWComplexity;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

public class SNV_JAVA implements Method{
    private Object[] result;
    private MWNumericArray matrix;
    private double[][] spectrabySNV;

    public  SNV_JAVA(double[][] data){
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
    }

    @Override
    public void invokeMethod() {
        try {
            SNVMATLAB snvmatlab=new SNVMATLAB();
            //the first integer parameter means how many objects will return in result
            result=snvmatlab.SNV(1, matrix);
        } catch (MWException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parseResult() {
        spectrabySNV=(double[][]) ((MWNumericArray)result[0]).toDoubleArray();
    }

    @Override
    public double[][] getResult() {
        return spectrabySNV;
    }


}
