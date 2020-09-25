package stackAndQueue.four;

/**
 在构造PetEnterQueue类时，pet是用户原有的实例，count就是这个实例的时间戳。
 实现的队列其实是 PetEnterQueue 类的实例。大体说来，首先有一个不断累加的数据项，用来表示实例进队列的时间；
 同时有两个队列，一个是只放dog类实例的队列dogQ，另一个是只放cat类实例的队列catQ。
 */
public class PetEnterQueue {
    private Pet pet;
    private long count;

    public PetEnterQueue(Pet pet, long count){
        this.pet = pet;
        this.count = count;
    }

    public Pet getPet(){
        return this.pet;
    }

    public long getCount(){
        return this.count;
    }

    public String getEnterPetType(){
        return this.pet.getPetType();
    }
}
