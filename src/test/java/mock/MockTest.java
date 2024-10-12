package mock;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MockTest {

    @Mock
    private Cat cat;
    @Mock
    private Dog dog;

    @Test
    public void test1() {
        Cat halfCat = cat.createHalfCat();
        Dog halfDog = dog.createHalfDog();
        // проверь, что createHalfCat() вызвался один раз
        Mockito.verify(cat, Mockito.times(1)).createHalfCat();
        // проверь, что createHalfDog() вызвался один раз
        Mockito.verify(dog, Mockito.times(1)).createHalfDog();
    }

    @Test
    public void test2() {
        // стаб, возвращающий половину кота с параметрами - 2 лапы, говорит "Я самый умный"
        Mockito.when(cat.createHalfCat()).thenReturn(new Cat(2, "Я самый умный"));
        // стаб, возвращающий половину пса с параметрами - 2 лапы, говорит "Я самый весёлый"
        Mockito.when(dog.createHalfDog()).thenReturn(new Dog(2, "Я самый весёлый"));
        Cat halfCat = cat.createHalfCat();
        Dog halfDog = dog.createHalfDog();
        CatDog catDog = new CatDog(halfCat, halfDog);
        // проверка, что у Котопса 4 лапы
        Assert.assertEquals(4, catDog.getLegsCount());
        // проверка, что Котопёс говорит "Единственный в мире малыш Котопёс"
        Assert.assertEquals("Единственный в мире малыш Котопёс", catDog.getSound());
    }
}
