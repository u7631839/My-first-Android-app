package com.example.myapplication.tools;

import com.example.myapplication.entity.Good;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GenerateGoodsData {
    private static final int NUMBER_OF_Goods = 2500;  // Change as needed
    private Faker faker = new Faker();
    private Set<String> existingGids = new HashSet<>();

    private String[] uids = {
            "1P1PKTkmiSgOw7N0tXErDQlPH0G2",
            "MKnd34ymUvP1SEJhwA91vMLC0iK2",
            "ayajyKzkEUgYYq0ZF4M6sab4Mq33",
            "k80sOh0okTb2udBun05dW16G4Nc2"
    };

    public List<Good> multiple_signup() {
        List<Good> list=new ArrayList<>();
        for (int i=0;i<NUMBER_OF_Goods;i++){
            list.add(generateUniqueGoods());
        }
        return list;

    }

    public Good generateUniqueGoods() {
        String gid;
        do {
            gid = faker.idNumber().valid();
        } while (existingGids.contains(gid));
        existingGids.add(gid);

        String name = faker.commerce().productName();
        //String category = faker.commerce().department();
        String category = faker.options().option("Industrial", "Sports", "Shoes", "Grocery", "Garden",
                "Automotive", "Kids", "Trays", "Appliances", "Parts", "Accessories", "Refrigerator",
                "Dishwasher", "Clothes", "Electronics", "Furniture", "Tools", "Toys", "Kitchenware",
                "Gadgets", "Jewelry", "Books", "Sports", "Beauty", "Health", "Automotive",
                "Home", "Office", "Outdoor", "Baby", "Pet", "Food", "Beverages", "Music", "Movies",
                "Games", "Art", "Crafts", "Stationery", "Travel", "Fitness", "Fashion", "Shoes",
                "Bags", "Watches", "Electrical", "Lighting", "Home Decor", "Garden", "DIY", "Camping",
                "Hiking", "Cycling", "Swimming", "Skiing", "Snowboarding", "Surfing", "Fishing", "Hunting",
                "Photography", "Computers", "Laptops", "Tablets", "Smartphones", "Cameras", "Printers",
                "Monitors", "Speakers", "Headphones", "Microphones", "Projectors", "Scanners", "Keyboards",
                "Mice", "Networking", "Storage", "Software", "Accessories", "Components", "Peripherals", "Security",
                "Surveillance", "Home Theater", "Audio", "Video", "Gaming", "VR", "AR", "Smart Home", "Smartwatches",
                "Fitness Trackers", "Virtual Assistants", "Smart Lighting", "Smart Locks",
                "Smart Thermostats", "Smart Appliances", "Smart Security", "Smart Cameras",
                "Smart Speakers", "Smart Displays", "Smart Plugs", "Smart Switches", "Smart Hubs",
                "Smart Sensors", "Smart Doorbells", "Smart Blinds", "Smart Irrigation", "Smart Gardening",
                "Industrial", "Sports", "Shoes", "Grocery", "Garden", "Automotive", "Kids",
                "Trays", "Appliances", "Parts", "Accessories","Romance Book", "Mystery Book", "Thriller Book",
                "Science Fiction Book", "Fantasy Book", "Biography Book", "History Book", "Self-Help Book",
                "Cooking Book", "Art Book", "Poetry Book", "Business Book", "Travel Book", "Health Book",
                "Education Book", "Children's Book", "Graphic Novels Book", "Religion Book", "Sports Book", "Science Book",
                "T-shirt", "Jeans", "Dress", "Skirt", "Blouse", "Sweater", "Hoodie",
                "Jacket", "Coat", "Suit", "Blazer", "Pants", "Shorts", "Leggings", "Socks",
                "Underwear", "Bra", "Swimsuit", "Pajamas", "Robe", "Shirt", "Top", "Tank Top",
                "Cardigan", "Vest", "Tunic", "Jumpsuit", "Romper", "Bodysuit", "Sweatshirt", "Pullover",
                "Halter Top", "Crop Top", "Camisole", "Kimono", "Poncho", "Cape", "Sarong", "Shawl",
                "Scarf", "Gloves", "Hat", "Beanie", "Cap", "Headband", "Bandana", "Tie", "Bowtie",
                "Necktie", "Belt", "Suspenders", "Sunglasses", "Glasses", "Watch", "Bracelet",
                "Necklace", "Earrings", "Ring", "Anklet", "Bracelet", "Handbag", "Clutch", "Tote",
                "Backpack", "Messenger Bag", "Crossbody Bag", "Wallet", "Purse", "Briefcase", "Suitcase",
                "Luggage", "Shoes", "Sneakers", "Sandals", "Boots", "Heels", "Flats", "Loafers", "Oxfords",
                "Slippers", "Flip Flops", "Espadrilles", "Wedges", "Mules", "Clogs", "Platform Shoes", "Trainers",
                "Running Shoes", "Dress Shoes", "Work Boots", "Rain Boots", "Hiking Boots", "Snow Boots",
                "Slip-On Shoes", "Athletic Shoes", "Dance Shoes", "Ballet Flats", "Pointed Toe Shoes",
                "Peep Toe Shoes", "Open Toe Shoes", "Closed Toe Shoes", "High Tops", "Low Tops", "Mid Tops",
                "Tights", "Stockings", "Leg Warmers", "Garters", "Garter Belt", "Corset", "Bralette",
                "Bustier", "Lingerie", "Nightgown", "Chemise", "Teddy", "Bikini", "Thong", "Boyshorts",
                "Boxers", "Briefs", "Boxer Briefs", "Trunks", "Long Johns", "Bralette", "Bustier", "Lingerie",
                "Nightgown", "Chemise", "Teddy", "Bikini", "Thong", "Boyshorts", "Boxers", "Briefs", "Boxer Briefs",
                "Trunks", "Long Johns","Television", "Refrigerator", "Washing Machine", "Dryer", "Dishwasher",
                "Microwave", "Oven", "Stove", "Toaster", "Blender", "Coffee Maker", "Kettle", "Juicer",
                "Food Processor", "Rice Cooker", "Slow Cooker", "Air Fryer", "Electric Grill", "Electric Skillet",
                "Electric Wok", "Electric Mixer", "Electric Can Opener", "Electric Knife", "Electric Toothbrush",
                "Hair Dryer", "Hair Straightener", "Curling Iron", "Electric Shaver", "Electric Razor", "Electric Toothbrush",
                "Electric Blanket", "Heating Pad", "Space Heater", "Air Conditioner", "Fan", "Humidifier", "Dehumidifier",
                "Air Purifier", "Vacuum Cleaner", "Steam Cleaner", "Iron", "Ironing Board", "Garment Steamer", "Handheld Vacuum",
                "Robot Vacuum", "Water Dispenser", "Water Heater", "Water Purifier", "Electric Fireplace", "Electric Heater",
                "Electric Kettle", "Electric Griddle", "Electric Pressure Cooker", "Electric Smoker", "Electric Fryer",
                "Electric Waffle Maker", "Electric Ice Cream Maker", "Electric Popcorn Maker", "Electric Yogurt Maker",
                "Electric Bread Maker", "Electric Pasta Maker", "Electric Egg Cooker", "Electric Wine Opener", "Electric Can Opener",
                "Electric Jar Opener", "Electric Knife Sharpener", "Electric Food Warmer", "Electric Food Dehydrator",
                "Electric Food Slicer", "Electric Food Chopper", "Electric Food Grinder", "Electric Food Steamer",
                "Electric Food Scale", "Electric Food Mixer", "Electric Food Blender", "Electric Food Juicer", "Electric Food Mill",
                "Electric Food Whisk", "Electric Food Grater", "Electric Food Peeler", "Electric Food Squeezer", "Electric Food Masher", "Electric Food Press", "Electric Food Dicer", "Electric Food Shredder", "Electric Food Spiralizer", "Electric Food Slicer", "Electric Food Chopper", "Electric Food Grinder", "Electric Food Steamer", "Electric Food Scale", "Electric Food Mixer", "Electric Food Blender", "Electric Food Juicer", "Electric Food Mill", "Electric Food Whisk", "Electric Food Grater", "Electric Food Peeler", "Electric Food Squeezer", "Electric Food Masher", "Electric Food Press",
                "Electric Food Dicer", "Electric Food Shredder", "Electric Food Spiralizer","Pencil",
                "Pen", "Notebook", "Binder", "Highlighter", "Eraser", "Ruler", "Calculator", "Scissors", "Glue",
                "Markers", "Crayons", "Paint", "Brush", "Clipboard", "Stapler", "Staples", "Paperclips", "Tape",
                "Index Cards", "Post-it Notes", "Whiteboard", "Dry Erase Marker", "Chalk", "Chalkboard",
                "Protractor", "Compass", "Graph Paper", "Calculator", "Flashcards", "Dictionary", "Thesaurus",
                "Textbook", "Laptop", "Computer", "Headphones", "USB Drive", "Desk Organizer", "Desk Lamp",
                "Bookends", "Bookmarks", "Desk Calendar", "Desk Pad", "Desk Chair",
                "Bulletin Board", "Push Pins", "Magnifying Glass", "Microscope", "Telescope", "Abacus", "Globe"
                );
        Double price = faker.number().randomDouble(2, 10, 10000);
        //Integer clicks = faker.number().numberBetween(0, 10000);
        String brand = faker.company().name();
       // Integer supermarketid = faker.number().numberBetween(1, 100);

        double minLon = 148.719900;
        double maxLon = 149.399123;

        double minLat = 35.147100;
        double maxLat = 35.920800;

        double lon = minLon + (maxLon - minLon) * faker.random().nextDouble();
        double lat = minLat + (maxLat - minLat) * faker.random().nextDouble();

        System.out.println("Random Lon: " + lon);
        System.out.println("Random Lat: " + lat);


        long registerTime;  // Current date and time
        registerTime = System.currentTimeMillis();


        // 创建一个随机数生成器
        Random random = new Random();

        // 从数组中随机选择一个字符串
        int randomIndex = random.nextInt(uids.length);
        String randomUid = uids[randomIndex];

        //Integer year = faker.number().numberBetween(1900,2023);
        //String location = faker.options().option("ACT","VIC","NSW");
       // String uid,  String name, String category, Double price, long registerTime, int isDelete, Integer clicks, String brand,  String gid, double lon, double lat
        return new Good(randomUid, name, category, price, registerTime,0,0,brand,gid,lon,lat);
    }
}
