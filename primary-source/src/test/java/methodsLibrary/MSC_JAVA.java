package methodsLibrary;

import MSC_MATLAB.MSCMATLAB;
import SNV_MATLAB.SNVMATLAB;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWComplexity;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

public class MSC_JAVA implements Method{
    private Object[] result;
    private MWNumericArray matrix;
    private MWNumericArray RefSpectrum;
    private double[][] spectrabyMSC;


    public void setParameters(double[][] refSpectrum) {
        /*------convert a double array into a matrix*/
        //set size of matrix
        int[] n={refSpectrum.length,refSpectrum[0].length};
        RefSpectrum=MWNumericArray.newInstance(n, MWClassID.DOUBLE, MWComplexity.REAL);
        /* Set matrix values */
        int[] index = {1, 1};
        for (index[0]= 1; index[0] <= n[0]; index[0]++) {
            for (index[1] = 1; index[1] <= n[1]; index[1]++) {
                //index of the first element in MATLAB is（1,1), but index of two-dimensional double array in Java is [0,0]
                RefSpectrum.set(index,refSpectrum[index[0]-1][index[1]-1]);
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
            MSCMATLAB mscmatlab=new MSCMATLAB();
            //the first integer parameter means how many objects will return in result
            result=mscmatlab.MSC(1, matrix,RefSpectrum);
        } catch (MWException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void parseResult() {
        spectrabyMSC=(double[][]) ((MWNumericArray)result[0]).toDoubleArray();
    }

    @Override
    public double[][] getResult() {
        return spectrabyMSC;
    }
}
