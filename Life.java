public class Life implements ILife {

    String[] cellMap = new String[]  {  "     ",
                                        "     ",
                                        "     ",
                                        "     ",
                                        "     " };


  
  public static void main(String[] args) {
    Life l = new Life(new String[] {  "     ",
                                      "     ",
                                      " *** ",
                                      "     ",
                                      "     " });
    l = (Life) l.nextGeneration();
  }

  //konstruktor
  public Life() {
    nukeAll();
  }
  
  //konstruktor overload
  public Life(String[] setup) {
    this();
    for (int x = 0; x < setup.length; x++) {
      for (int y = 0; y < setup[x].length(); y++) {
        if (setup[x].charAt(y) != ' ') {
          setAlive(x, y);
        }
      }
    }
  }

  @Override
  public void nukeAll() {
    // TODO Auto-generated method stub
    for (int x = 0; x < cellMap.length; x++) {
      for (int y = 0; y < cellMap[x].length(); y++) {
        setDead(x,y);
      }
    }
  }


  @Override
  public void setAlive(int x, int y) {
    // TODO Auto-generated method stub
    cellMap[x] = replaceChar(cellMap[x], '*', y);
  }

  @Override
  public void setDead(int x, int y) {
    // TODO Auto-generated method stub
    cellMap[x] = replaceChar(cellMap[x], ' ', y);
  }

  @Override
  public boolean isAlive(int x, int y) {
    // TODO Auto-generated method stub
    //check if the cell isAlive...
    if(cellMap[x].charAt(y) != ' ') {
      return true;
    }
    return false;
  }

  @Override //regeln
  public ILife nextGeneration() {
    // TODO Auto-generated method stub
    //printCell(cellMap);
    Life t = new Life(cellMap);

   //check every cell
    for (int x = 0; x < cellMap.length; x++) {
      for (int y = 0; y < cellMap[x].length(); y++) {
         if(checkCellCountAroundCell(x, y) == 3) {
           t.setAlive(x,y);
         }
         if(checkCellCountAroundCell(x, y) < 2) {
           t.setDead(x,y);
         }
         if(isAlive(x,y) && ((checkCellCountAroundCell(x, y) == 2) ||
                             (checkCellCountAroundCell(x, y) == 3))) {
           //nothing
         }
         if(isAlive(x,y) && (checkCellCountAroundCell(x, y) > 3)) {
           t.setDead(x,y);
         }
      }
    }
    printCell();
    t.printCell();
    
    return (ILife) t; 
    //ILife nextGen = t.nextGeneration();
    //soll iLife zurückgeben
    // return null;
  }
  
  public int checkCellCountAroundCell(int x, int y) {
  //Der hardcoded scheiß funktioniert!
  int cellCount = 0;

    //center Cells
    if((x > 0) && (x < 4) &&
       (y > 0) && (y < 4)) {

      if(isAlive(x-1, y-1))
        cellCount++;
      if(isAlive(x-1, y))
        cellCount++;
      if(isAlive(x-1, y+1))
        cellCount++;
      if(isAlive(x, y-1))
        cellCount++;
      if(isAlive(x, y+1))
       cellCount++;
      if(isAlive(x+1, y-1))
       cellCount++;
      if(isAlive(x+1, y))
       cellCount++;
      if(isAlive(x+1, y+1))
       cellCount++;
      //System.out.print("Center count at "+x+","+y+" = "+cellCount+"\n");
    }

    //corners
    if(x == 0 && y == 0) {
      if(isAlive(x+1, y))
        cellCount++;
      if(isAlive(x, y+1))
        cellCount++;
      if(isAlive(x+1, y+1))
        cellCount++;
      //System.out.print("Corner count at "+x+","+y+" = "+cellCount+"\n");
    }

    if(x == 0 && y == 4) {
      if(isAlive(x, y-1))
        cellCount++;
      if(isAlive(x+1, y))
        cellCount++;
      if(isAlive(x+1, y-1))
        cellCount++;
      //System.out.print("Corner count at "+x+","+y+" = "+cellCount+"\n");
    }

    if(x == 4 && y == 0) {
      if(isAlive(x, y+1))
        cellCount++;
      if(isAlive(x-1, y))
        cellCount++;
      if(isAlive(x-1, y+1))
        cellCount++;
      //System.out.print("Corner count at "+x+","+y+" = "+cellCount+"\n");
    }

    if(x == 4 && y == 4) {
      if(isAlive(x, y-1))
        cellCount++;
      if(isAlive(x-1, y))
        cellCount++;
      if(isAlive(x-1, y-1))
        cellCount++;
      //System.out.print("Corner count at "+x+","+y+" = "+cellCount+"\n");
    }

    //sides
    if(x == 0 && 4 > y && y > 0) {
      if(isAlive(x+1, y-1))
        cellCount++;
      if(isAlive(x+1, y))
        cellCount++;
      if(isAlive(x+1, y+1))
        cellCount++;
      //System.out.print("Side   count at "+x+","+y+" = "+cellCount+"\n");
    }

    if(x == 4 && 4 > y && y > 0) {
      if(isAlive(x-1, y-1))
        cellCount++;
      if(isAlive(x-1, y))
        cellCount++;
      if(isAlive(x-1, y+1))
        cellCount++;
      //System.out.print("Side   count at "+x+","+y+" = "+cellCount+"\n");
    }

    if(y == 0 && 4 > x && x > 0) {
      if(isAlive(x-1, y+1))
        cellCount++;
      if(isAlive(x, y+1))
        cellCount++;
      if(isAlive(x+1, y+1))
        cellCount++;
      //System.out.print("Side   count at "+x+","+y+" = "+cellCount+"\n");
    }

    if(y == 4 && 4 > x && x > 0) {
      if(isAlive(x-1, y-1))
        cellCount++;
      if(isAlive(x, y-1))
        cellCount++;
      if(isAlive(x+1, y-1))
        cellCount++;
      //System.out.print("Side   count at "+x+","+y+" = "+cellCount+"\n");
    }
    return cellCount;
  }


  // https://www.baeldung.com
  public String replaceChar(String str, char ch, int position) {
    String temp = str.substring(0, position) + ch + str.substring(position+1);
    return temp;
  }

  public void printCell() {
		// TODO Auto-generated method stub
    for(int i = 0; i < cellMap.length; i++) {
      System.out.print(cellMap[i]+ "\n");
    }
	}


  public void printCell(String[] map) {
		// TODO Auto-generated method stub
    for(int i = 0; i < map.length; i++) {
      System.out.print(map[i]+ "\n");
    }
	}


}