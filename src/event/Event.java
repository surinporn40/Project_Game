package event;

import Character.*;

public class Event {
    public static boolean checkHit(Boy boy,Slime slime,int boySize,int slimeHeight){
        if(boy.x+boySize>slime.x&&boy.x<slime.x) {
            if(boy.y+boySize>=slime.y-slimeHeight) {
                return true;
            }
        }
        return false;
}

public static void gameStop(Slime[] slime,Environment[] env) {

}
}
