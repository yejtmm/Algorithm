package stackAndQueue.four;

import java.util.LinkedList;
import java.util.Queue;

/**
 实现一种狗猫队列的结构，要求如下：
 ● 用户可以调用add方法将cat类或dog类的实例放入队列中；
 ● 用户可以调用pollAll方法，将队列中所有的实例按照进队列的先后顺序依次弹出；
 ● 用户可以调用pollDog方法，将队列中dog类的实例按照进队列的先后顺序依次弹出；
 ● 用户可以调用pollCat方法，将队列中cat类的实例按照进队列的先后顺序依次弹出；
 ● 用户可以调用isEmpty方法，检查队列中是否还有dog或cat的实例；
 ● 用户可以调用isDogEmpty方法，检查队列中是否有dog类的实例；
 ● 用户可以调用isCatEmpty方法，检查队列中是否有cat类的实例。

 将不同的实例盖上时间戳的方法，但是又不能改变用户本身的类，所以定义一个新的类

 PetEnterQueue

 在加入实例时，如果实例是dog，就盖上时间戳，生成对应的PetEnterQueue类的实例，然后放入 dogQ;
 如果实例是 cat，就盖上时间戳，生成对应的PetEnterQueue 类的实例，然后放入catQ。

 只想弹出dog类的实例时，从dogQ里不断弹出即可。
 只想弹出 cat 类的实例时，从 catQ 里不断弹出即可。

 想按实际顺序弹出实例时，因为 dogQ的队列头表示所有 dog实例中最早进队列的实例，同时catQ的队列头表示所有的cat实例中最早进队列的实例。
 则比较这两个队列头的时间戳，谁更早，就弹出谁。

 */
public class CatDogQueue {

    private Queue<PetEnterQueue> dogQ;
    private Queue<PetEnterQueue> catQ;
    private long count;

    public CatDogQueue(){
        this.dogQ = new LinkedList<>();
        this.catQ = new LinkedList<>();
    }

    /**
     * 入队
     * @param pet
     */
    public void add(Pet pet){
        if(pet.getPetType().equals("dog")){
            this.dogQ.add(new PetEnterQueue(pet, this.count++));
        }else if ((pet.getPetType().equals("cat"))){
            this.catQ.add(new PetEnterQueue(pet, this.count++));
        }else {
            throw new RuntimeException("错误");
        }
    }

    public Pet pollAll(){
        if(!this.dogQ.isEmpty() && !this.catQ.isEmpty()){
            if(this.dogQ.peek().getCount() < this.catQ.peek().getCount()){
                return this.dogQ.poll().getPet();
            }else {
                return this.catQ.poll().getPet();
            }
        }else if(!this.dogQ.isEmpty()){
            return this.dogQ.poll().getPet();
        }else if(!this.catQ.isEmpty()){
            return this.catQ.poll().getPet();
        }else{
            throw new RuntimeException("错误");
        }
    }

    public Dog pollDog(){
        if(!this.isDogQueueEmpty()){
            return (Dog) this.dogQ.poll().getPet();
        }else {
            throw new RuntimeException("狗队列是空的");
        }
    }

    public Cat pollCat(){
        if(!this.isCatQueueEmpty()){
            return (Cat) this.catQ.poll().getPet();
        }else {
            throw new RuntimeException("猫队列是空的");
        }
    }

    public boolean isEmpty(){
        return this.dogQ.isEmpty() && this.catQ.isEmpty();
    }

    public boolean isDogQueueEmpty(){
        return this.dogQ.isEmpty();
    }

    public boolean isCatQueueEmpty(){
        return this.catQ.isEmpty();
    }
}
