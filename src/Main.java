import game.Agent;
import game.GamePlayer;
import game.MyAgent;

public class Main {

    /**
     * starts the game player and waits for messages from the game master <br>
     * Command line options: [port]
     */
    public static void main(String[] args){
        try{
            Agent agent = new MyAgent();
            int port=4001;
            if(args.length>=1){
                port=Integer.parseInt(args[0]);
            }
            GamePlayer gp=new GamePlayer(port, agent);
            gp.waitForExit();
        }catch(Exception ex){
            ex.printStackTrace();
            System.exit(-1);
        }
    }
}
