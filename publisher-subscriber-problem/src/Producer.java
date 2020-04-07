import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private BlockingQueue<Message> queue;

    public Producer(BlockingQueue<Message> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        for (int i = 0; i < 100; i++) {
            Message message = new Message(""+i);
            try {
                Thread.sleep(i);
                queue.put(message);
                System.out.println("Produced: "+ message.getMessage());
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        Message message = new Message("exit");
        try{
            queue.put(message);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
