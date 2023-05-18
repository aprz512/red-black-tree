package com.aprz;

import java.util.Random;

public class Test {
    public static void main(String[] args) {
        testDelete();
        testInsert();
    }

    private static void testDelete() {

        Log.d("Testing Red-Black tree deletion ...\n");

        RedBlackTree test1 = null;
        RedBlackTree test2 = null;

        test1 = new Builder().build(
                "(10," +
                        "(5,#,(9,#,#))," +
                        "(15,#,#)" +
                        ")",
                "BB#R##B##");
        test1.delete(5);

        test2 = new Builder().build(
                "(10," +
                        "(9,#,#)," +
                        "(15,#,#)" +
                        ")",
                "BB##B##");

        Verify.compareTree(test1, test2);

        // --------------------------------------------------------------

        test1 = new Builder().build(
                "(10," +
                        "(5," + // delete - red
                        "(2,#,#)," + // T1
                        "(6,#,(7,#,#))" + // successor
                        ")," +
                        "(15,#,#)" + // T1
                        ")",
                "BRB##B#R##B##");
        test1.delete(5);

        test2 = new Builder().build(
                "(10," +
                        "(6," + // T1
                        "(2,#,#)," + // T1
                        "(7,#,#)" +
                        ")," +
                        "(15,#,#)" + // T1
                        ")",
                "BRB##B##B##");

        Verify.compareTree(test1, test2);

        // --------------------------------------------------------------

        test1 = new Builder().build(
                "(10," +
                        "(5," + // delete - black
                        "(2,#,#)," + // T1
                        "(6,#,(7,#,#))" + // successor
                        ")," +
                        "(15,(12,#,#),(16,#,#))" + // T2
                        ")",
                "BBB##B#R##BB##B##");
        test1.delete(5);

        test2 = new Builder().build(
                "(10," +
                        "(6," + // T2
                        "(2,#,#)," + // T1
                        "(7,#,#)" +
                        ")," +
                        "(15,(12,#,#),(16,#,#))" + // T2
                        ")",
                "BBB##B##BB##B##");

        Verify.compareTree(test1, test2);

        // --------------------------------------------------------------

        test1 = new Builder().build(
                "(10," +
                        "(5," + // delete - black
                        "(2,#,#)," + // T0
                        "(7,#,#)" + // successor
                        ")," +
                        "(15,#,#)" + // T1
                        ")",
                "BBR##R##B##");
        test1.delete(5);

        test2 = new Builder().build(
                "(10," +
                        "(7," + // delete - black
                        "(2,#,#)," + // T0
                        "#" +
                        ")," +
                        "(15,#,#)" + // T1
                        ")",
                "BBR###B##");

        Verify.compareTree(test1, test2);

        // --------------------------------------------------------------

        test1 = new Builder().build(
                "(4," +
                        "(2,(1,#,#),(3,#,#))," +
                        "(6," + // delete
                        "(5,#,#)," +
                        "(10," +
                        "(8,(7,#,#),(9,#,#))," + // 7 successor
                        "(11,#,#)" +
                        ")," +
                        ")" +
                        ")",
                "BBB##B##BB##RBR##R##B##");
        test1.delete(6);

        test2 = new Builder().build(
                "(4," +
                        "(2,(1,#,#),(3,#,#))," +
                        "(7," + // delete
                        "(5,#,#)," +
                        "(10," +
                        "(8,#,(9,#,#))," + // 7 successor
                        "(11,#,#)" +
                        ")," +
                        ")" +
                        ")",
                "BBB##B##BB##RB#R##B##");

        Verify.compareTree(test1, test2);

        // --------------------------------------------------------------

        test1 = new Builder().build(
                "(8," +
                        "(4,(2,(1,#,#),(3,#,#)),(6,(5,#,#),(7,#,#)))," + // T3
                        "(12," + // delete
                        "(10,(9,#,#),(11,#,#))," +
                        "(17," +
                        "(15,(13,#,(14,#,#)),(16,#,#))," + // 13 successor
                        "(19,(18,#,#),(20,#,#))" +
                        ")," +
                        ")" +
                        ")",
                "BBBB##B##BB##B##BBB##B##BRB#R##B##RB##B##");
        test1.delete(12);

        test2 = new Builder().build(
                "(8," +
                        "(4,(2,(1,#,#),(3,#,#)),(6,(5,#,#),(7,#,#)))," + // T3
                        "(13," + // successor
                        "(10,(9,#,#),(11,#,#))," +
                        "(17," +
                        "(15,(14,#,#),(16,#,#))," + // recoloring
                        "(19,(18,#,#),(20,#,#))" +
                        ")," +
                        ")" +
                        ")",
                "BBBB##B##BB##B##BBB##B##BRB##B##RB##B##");

        Verify.compareTree(test1, test2);

        // --------------------------------------------------------------

        test1 = new Builder().build(
                "(10," +
                        "(5,(2,#,#),(9,#,#))," +
                        "(30,(25,#,#),(40,(38,#,#),#))" +
                        ")",
                "BRB##B##RB##BR###");
        test1.delete(38);

        test2 = new Builder().build(
                "(10," +
                        "(5,(2,#,#),(9,#,#))," +
                        "(30,(25,#,#),(40,#,#))" +
                        ")",
                "BRB##B##RB##B##");

        Verify.compareTree(test1, test2);

        // --------------------------------------------------------------

        test1 = new Builder().build(
                "(10," +
                        "(5,(2,#,#),(9,#,#))," +
                        "(30,(25,#,#),(40,(35,#,(38,#,#)),(50,#,#)))" +
                        ")",
                "BBB##B##BB##RB#R##B##");
        test1.delete(30);

        test2 = new Builder().build(
                "(10," +
                        "(5,(2,#,#),(9,#,#))," +
                        "(35,(25,#,#),(40,(38,#,#),(50,#,#)))" +
                        ")",
                "BBB##B##BB##RB##B##");

        Verify.compareTree(test1, test2);

        // --------------------------------------------------------------

        test1 = new Builder().build(
                "(10," +
                        "(5,#,#)," +
                        "(20,(15,#,#),(30,#,#))" +
                        ")",
                "BB##RB##B##");
        test1.delete(15);

        test2 = new Builder().build(
                "(10," +
                        "(5,#,#)," +
                        "(20,#,(30,#,#))" +
                        ")",
                "BB##B#R##");

        Verify.compareTree(test1, test2);

        // --------------------------------------------------------------

        test1 = new Builder().build(
                "(10," +
                        "(5,(1,#,#),(7,#,#))," +
                        "(20,(15,#,#),(30,#,#))" +
                        ")",
                "BBB##B##BB##B##");
        test1.delete(15);

        test2 = new Builder().build(
                "(10," +
                        "(5,(1,#,#),(7,#,#))," +
                        "(20,#,(30,#,#))" +
                        ")",
                "BRB##B##B#R##");

        Verify.compareTree(test1, test2);

        // --------------------------------------------------------------

        test1 = new Builder().build(
                "(10," +
                        "(5,(1,#,#),(7,#,#))," +
                        "(20,(15,#,#),(30,(25,#,#),(40,#,#)))" +
                        ")",
                "BBB##B##BB##RB##B##");
        test1.delete(15);

        test2 = new Builder().build(
                "(10," +
                        "(5,(1,#,#),(7,#,#))," +
                        "(30,(20,#,(25,#,#)),(40,#,#))" +
                        ")",
                "BBB##B##BB#R##B##");

        Verify.compareTree(test1, test2);

        // --------------------------------------------------------------

        test1 = new Builder().build(
                "(10," +
                        "(5,(1,#,#),(7,#,#))," +
                        "(30,(25,(20,#,#),(28,#,#)),(40,#,#))" +
                        ")",
                "BBB##B##BRB##B##B##");
        test1.delete(1);

        test2 = new Builder().build(
                "(25," +
                        "(10,(5,#,(7,#,#)),(20,#,#))," +
                        "(30,(28,#,#),(40,#,#))" +
                        ")",
                "BBB#R##B##BB##B##");

        Verify.compareTree(test1, test2);

        // --------------------------------------------------------------

        test1 = new Builder().build(
                "(50," +
                        "(20,(15,#,#),(35,#,#))," +
                        "(65," +
                        "(55,#,#)," +
                        "(70,(68,#,#),(80,#,(90,#,#)))" +
                        ")" +
                        ")",
                "BBB##B##BB##RB##B#R##");
        test1.delete(55);

        test2 = new Builder().build(
                "(50," +
                        "(20,(15,#,#),(35,#,#))," +
                        "(70," +
                        "(65,#,(68,#,#))," +
                        "(80,#,(90,#,#))" +
                        ")" +
                        ")",
                "BBB##B##BB#R##B#R##");

        Verify.compareTree(test1, test2);

        // --------------------------------------------------------------

        // test1 = new Builder().build(
        // "(50," +
        // "(20,(15,#,#),(35,#,#))," +
        // "(65," +
        // "(55,#,#)," +
        // "(70,(68,#,#),(80,#,(90,#,#)))" +
        // ")" +
        // ")",
        // "BBB##B##BB##RB##B#R##");
        test1.delete(20);

        test2 = new Builder().build(
                "(50," +
                        "(35,(15,#,#),#)," +
                        "(70," +
                        "(65,#,(68,#,#))," +
                        "(80,#,(90,#,#))" +
                        ")" +
                        ")",
                "BBR###RB#R##B#R##");

        Verify.compareTree(test1, test2);

        // --------------------------------------------------------------

        test1.delete(90);

        test2 = new Builder().build(
                "(50," +
                        "(35,(15,#,#),#)," +
                        "(70," +
                        "(65,#,(68,#,#))," +
                        "(80,#,#)" +
                        ")" +
                        ")",
                "BBR###RB#R##B##");

        Verify.compareTree(test1, test2);

        // --------------------------------------------------------------

        test1.delete(80);

        test2 = new Builder().build(
                "(50," +
                        "(35,(15,#,#),#)," +
                        "(68," +
                        "(65,#,#)," +
                        "(70,#,#)" +
                        ")" +
                        ")",
                "BBR###RB##B##");

        Verify.compareTree(test1, test2);

        // --------------------------------------------------------------

        test1.delete(50);

        test2 = new Builder().build(
                "(65," +
                        "(35,(15,#,#),#)," +
                        "(68,#,(70,#,#))" +
                        ")" +
                        ")",
                "BBR###B#R##");

        Verify.compareTree(test1, test2);

        // --------------------------------------------------------------

        test1.delete(35);

        test2 = new Builder().build(
                "(65," +
                        "(15,#,#)," +
                        "(68,#,(70,#,#))" +
                        ")" +
                        ")",
                "BB##B#R##");

        Verify.compareTree(test1, test2);

        // --------------------------------------------------------------

        test1.delete(15);

        test2 = new Builder().build(
                "(68,(65,#,#),(70,#,#))",
                "BB##B##");

        Verify.compareTree(test1, test2);

        // --------------------------------------------------------------

        test1.delete(65);

        test2 = new Builder().build(
                "(68,#,(70,#,#))",
                "B#R##");
        Verify.compareTree(test1, test2);
    }

    public static void testInsert() {
        RedBlackTree tree1 = null;
        RedBlackTree tree2 = null;

        tree1 = new Builder().build(
                "(11," +
                        "(2," +
                        "(1,#,#)," +
                        "(7," +
                        "(5,#,#)," +
                        "(8,#,#)" +
                        ")" +
                        ")," +
                        "(14,#,(15,#,#))" +
                        ")",
                "B" +
                        "R" +
                        "B##" +
                        "B" +
                        "R##" +
                        "R##" +
                        "B#R##");

        tree1.insert(4);

        tree2 = new Builder().build(
                "(5,(2,(1,#,#),(4,#,#)),(11,(7,#,(8,#,#)),(14,#,(15,#,#))))",
                "B" +
                        "B" +
                        "B##" +
                        "B##" +
                        "B" +
                        "B#R##" +
                        "B#R##");

        Verify.compareTree(tree1, tree2);

        int loops = 50000;
        int iteration = 1000;

        RedBlackTree tree3 = new RedBlackTree();

        for (int i = 0; i < loops; ++i) {
            int key = new Random().nextInt(1000000);

            if (i % iteration == 0) {
                Log.d("insert " + i + " / " + loops + ", insert key = " + key);
            }

            tree3.insert(key);
            // Verify.printTree(tree3.root(), 0);
            Verify.verify(tree3);
        }

    }
}
