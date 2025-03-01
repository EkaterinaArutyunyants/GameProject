package bird2;

import city.cs.engine.World;



public class PipeFactory {
    private World world;
    private int pipeCounter = 0;
    private int maxPipeCounter = 5;
    private long nextPipeTime;
    private static float[] heightsUp = {15f,12f,17f};
    private static float[] heightsDown = {15f,17f,12f};
    private static int idxH = 0;


    public PipeFactory(World world) {
        this.world = world;
    }

    public void createNewPipeIfNeeded(){
        if (pipeCounter < maxPipeCounter && nextPipeTime < System.currentTimeMillis()) {
            pipeCounter--; //infinity pipes up
            pipeCounter++;
            nextPipeTime = System.currentTimeMillis() + 4000;
            new Pipe(heightsUp[idxH], heightsDown[idxH], world);
            idxH++;
            if (idxH == heightsUp.length) {
                idxH = 0;
            }

        }
    }
}
