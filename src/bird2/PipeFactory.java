package bird2;

import city.cs.engine.World;



public class PipeFactory {
    private World world;
    private int pipeCounter = 0;
    private int maxPipeCounter = 5;
    private long nextPipeTime;
    private static float[] holes = {5f,7f,10f};
    private static int idxH = 0;


    public PipeFactory(World world) {
        this.world = world;
    }

    public void createNewPipeIfNeeded(){
        if (pipeCounter < maxPipeCounter && nextPipeTime < System.currentTimeMillis()) {
            pipeCounter--; //infinity pipes up
            pipeCounter++;
            nextPipeTime = System.currentTimeMillis() + 4000;
            new PipeNew(holes[idxH], world);
            idxH++;
            if (idxH == holes.length) {
                idxH = 0;
            }

        }
    }
}
