

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.tensorflow.Graph;
import org.tensorflow.framework.initializers.Glorot;
import org.tensorflow.framework.initializers.Initializer;
import org.tensorflow.framework.initializers.VarianceScaling;
import org.tensorflow.ndarray.*;
import org.tensorflow.op.Ops;
import org.tensorflow.op.core.Placeholder;
import org.tensorflow.op.core.Variable;
import org.tensorflow.op.math.Sigmoid;
import org.tensorflow.types.TFloat32;
import org.tensorflow.types.TInt32;
import org.tribuo.Trainer;
import org.tribuo.interop.tensorflow.*;
import org.tribuo.regression.Regressor;


public class EnemyAgent {
    public int color=1;
    public EnemyAgent(){}
    public void train(int numGames){


    }
    public TensorFlowTrainer defineModel(){
        int numCellsHorizontal=GameOfLifeCompetitive.canvasWidth/GameOfLifeCompetitive.cellSideLength;
        int numCellsVertical=GameOfLifeCompetitive.canvasHeight/GameOfLifeCompetitive.cellSideLength;
        Graph agentModelGraph=new Graph();
        Ops agentModelOperations=Ops.create(agentModelGraph);
        String agentModelInputName="BOARD_STATE";
        Initializer agentModelInitializer=new Glorot<TFloat32>(VarianceScaling.Distribution.TRUNCATED_NORMAL, Trainer.DEFAULT_SEED);
        Placeholder agentModelInput=agentModelOperations.withName(agentModelInputName).placeholder(TFloat32.class, Placeholder.shape(Shape.of(-1, (long) numCellsHorizontal *numCellsVertical)));
        Variable denseWeights1=agentModelOperations.variable(agentModelInitializer.call(agentModelOperations, agentModelOperations.array((long) numCellsHorizontal *numCellsVertical, 30L), TFloat32
        .class));
        Variable denseBiases1=agentModelOperations.variable(agentModelOperations.fill(agentModelOperations.array(30), agentModelOperations.constant(0.1f)));
        Sigmoid layerResult1=agentModelOperations.math.sigmoid(agentModelOperations.math.add(agentModelOperations.linalg.matMul(agentModelInput, denseWeights1), denseBiases1));
        Variable denseWeights2=agentModelOperations.variable(agentModelInitializer.call(agentModelOperations, agentModelOperations.array(20L, 30L), TFloat32.class));
        Variable denseBiases2=agentModelOperations.variable(agentModelOperations.fill(agentModelOperations.array(20), agentModelOperations.constant(0.1f)));
        Sigmoid layerResult2=agentModelOperations.math.sigmoid(agentModelOperations.math.add(agentModelOperations.linalg.matMul(layerResult1, denseWeights2), denseBiases2));
        Variable outputWeights=agentModelOperations.variable(agentModelInitializer.call(agentModelOperations, agentModelOperations.array(20L, 2L), TFloat32.class));
        Variable outputBiases=agentModelOperations.variable(agentModelOperations.fill(agentModelOperations.array(2), agentModelOperations.constant(0.1f)));
        Sigmoid outputLayerResult=agentModelOperations.math.sigmoid(agentModelOperations.math.add(agentModelOperations.linalg.matMul(layerResult1, outputWeights), outputBiases));
        String outputLayerName=outputLayerResult.op().name();
        GradientOptimiser optimiser= GradientOptimiser.ADAGRAD;
        Map<String,Float> gradientParameters= new HashMap(){{
            //var gradParams = Map.of("learningRate",0.1f,"initialAccumulatorValue",0.01f);
            put("learningRate", 0.1f);
            put("initialAccumulatorValue", 0.01f);
        }};
       DenseFeatureConverter agentModelDenseConverter=new DenseFeatureConverter(agentModelInputName);
       RegressorConverter agentModelOutputConverter=new RegressorConverter();
       TensorFlowTrainer<Regressor> agentModelTrainer=new TensorFlowTrainer<Regressor>(agentModelGraph, outputLayerName, optimiser, gradientParameters, agentModelDenseConverter, agentModelOutputConverter, 16, 30, 16, -1);
       agentModelGraph.close();
       return agentModelTrainer;


    }
    public int[] chooseAction(IntNdArray state){

        return new int[]{};
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
