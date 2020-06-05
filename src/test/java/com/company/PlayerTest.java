package com.company;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    TileMap tileMap = new TileMap("JavaGameProject/Map/map1.txt", 32);
    Player player = new Player(tileMap);

    @Test
    public void getItems() {
        int get = player.getItems();
        boolean good = false;
        if(get <= 3 && get >= 0 ){
            good = true;
        }
        Assert.assertEquals(true, good);
    }

    @Test
    public void setX() {
        player.setX(100);
        int x = (int) player.getX();
        assertEquals(100, x);

        player.setX(250);
        x = (int)player.getX();
        assertEquals(250, x);

        player.setX(-1500);
        x = (int)player.getX();
        assertEquals(-1500, x);

    }

    @Test
    public void setY() {
        player.setY(100);
        assertEquals(100, (int) player.getY());

        player.setY(200);
        assertEquals(200, (int)player.getY());

        player.setY(1000);
        assertEquals(1000, (int)player.getY());
    }


    @Test
    public void getHealth() {
        Player player = new Player(tileMap);
        int get = player.getHealth();
        boolean good = false;
        if(get <= 100 && get >= 0 ){
            good = true;
        }
        Assert.assertEquals(true, good);
    }


    @Test
    public void setStayRight() {
        player.setStayRight(true);
        boolean stayRight = player.getStayRight();
        assertEquals(true, stayRight);
        player.setStayRight(false);
        stayRight = player.getStayRight();
        assertEquals(false, stayRight);
    }


    @Test
    public void setStayLeft() {
        player.setStayLeft(true);
        boolean stayLeft = player.getStayLeft();
        assertEquals(true, stayLeft);
    }

    @Test
    public void setLookUp() {
        player.setLookUp(true);
        boolean lookUp = player.getLookUp();
        assertEquals(true, lookUp);
    }


    @Test
    public void setRun() {
        player.setRun(true);
        assertEquals(true, player.getRun());
    }

    @Test
    public void hit() throws InterruptedException {
        int hp = player.getHealth();
        Thread.sleep(1001);
        player.hit();
        System.out.println(player.getHealth());

        // ile zdrowia odjeło się w bohatera
        int deltaHp = hp - player.getHealth();

        // za jedną sekundę możemy zaatakować gracza tylko jeden raz
        for (int i = 0; i < 5; i++) {
            Thread.sleep(1001);
            player.hit();
            System.out.println(player.getHealth());
        }
        assertEquals(hp - (6*deltaHp), player.getHealth());
    }

    @Test
    public void bulletColl() {
        int prevHp = player.getHealth();
        try {
            Thread.sleep(1001);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BulletEnemy bullet = new BulletEnemy(tileMap, player.getX() +10, player.getY()+10, false);
        player.bulletColl(bullet);
        // pocisk trafiając w gracza odejmuje 15 jednostek hp
        assertEquals(prevHp - 15 ,player.getHealth());

        // pocisk nie trafia w gracza
        BulletEnemy bullet2 = new BulletEnemy(tileMap, 0, 0, true);
        player.bulletColl(bullet);
        // zdrowie się nie zmienia
        assertEquals(prevHp - 15 ,player.getHealth());

    }
}