import java.util.ArrayList;
public class AI {
	private boolean randomGuess, afterFirstHit, afterSecondHit, afterThirdHit, afterFourthHit;
	private int regionZeroRange, regionOneRange, regionTwoRange, regionThreeRange;
	public PlayerBoard playerBoard;
	private Point originalHit, secondHit, thirdHit, fourthHit;
	private ArrayList<Point> alreadyGuessed, misses;
	private ArrayList<Integer> afterFirstHitCasesTried, afterSecondHitCasesTried, afterThirdHitCasesTried, afterFourthHitCasesTried;
	
	public AI(PlayerBoard board) {
		randomGuess = true;
		afterFirstHit = false;
		afterSecondHit = false;
		afterThirdHit = false;
		afterFourthHit = false;
		regionZeroRange = 25;
		regionOneRange = 25;
		regionTwoRange = 25;
		regionThreeRange = 25;
		playerBoard = board;
		originalHit = new Point();
		alreadyGuessed = new ArrayList<>();
		misses = new ArrayList<>();
		afterFirstHitCasesTried = new ArrayList<>();
		afterSecondHitCasesTried = new ArrayList<>();
		afterThirdHitCasesTried = new ArrayList<>();
		afterFourthHitCasesTried = new ArrayList<>();
	}
	
	public ArrayList<Point> getGuesses(){
		return alreadyGuessed;
	}
	
	public int getRegion() {
		int region = (int)(Math.random() * 100);
		while(!inRange(region)) {
			region = (int)(Math.random() * 100);
		}
		if(region < 25) {
			return 0;
		}else if(region < 50) {
			return 1;
		}else if(region < 75) {
			return 2;
		}else {
			return 3;
		}
	}
	
	public boolean inRange(int num) {
		if(num < 25) {
			if(num <= regionZeroRange) {
				return true;
			}
		}else if(num < 50) {
			if(num <= regionOneRange + 25) {
				return true;
			}
		}else if(num < 75) {
			if(num <= regionTwoRange + 50) {
				return true;
			}
		}else {
			if(num <= regionThreeRange + 75) {
				return true;
			}
		}
		return false;
	}
	
	public boolean guess() {
		// -------------  Random Guess  -------------
		if(randomGuess) {
			int region = getRegion();
			int height, width;
			if(region == 0) {
				height = (int)(Math.random() * 5);
				width = (int)(Math.random() * 5);
			}else if(region == 1) {
				height = (int)(Math.random() * 5);
				width = (int)(Math.random() * 5 + 5);
			} else if(region == 2) {
				height = (int)(Math.random() * 5 + 5);
				width = (int)(Math.random() * 5);
			}else {
				height = (int)(Math.random() * 5 + 5);
				width = (int)(Math.random() * 5 + 5);
			}
			while(alreadyGuessed.contains(new Point(height, width))) {
				region = getRegion();
				if(region == 0) {
					height = (int)(Math.random() * 5);
					width = (int)(Math.random() * 5);
				}else if(region == 1) {
					height = (int)(Math.random() * 5);
					width = (int)(Math.random() * 5 + 5);
				} else if(region == 2) {
					height = (int)(Math.random() * 5 + 5);
					width = (int)(Math.random() * 5);
				}else {
					height = (int)(Math.random() * 5 + 5);
					width = (int)(Math.random() * 5 + 5);
				}
			}
			boolean leftBlock = false, rightBlock = false, topBlock = false, bottomBlock = false;
			if(height > 0) {
				if(misses.contains(new Point(height-1, width))) {
					topBlock = true;
				}
			}else {
				topBlock = true;
			}
			if(height < 10) {
				if(misses.contains(new Point(height+1, width))) {
					bottomBlock = true;
				}
			}else {
				bottomBlock = true;
			}
			if(width > 0) {
				if(misses.contains(new Point(height, width-1))) {
					leftBlock = true;
				}
			}else {
				leftBlock = true;
			}
			if(width < 10) {
				if(misses.contains(new Point(height, width+1))) {
					rightBlock = true;
				}
			}else {
				rightBlock = true;
			}
			if(rightBlock && leftBlock && topBlock && bottomBlock) {
				return false;
			}
			if(playerBoard.hitOrMiss(height, width)) {
				randomGuess = false;
				afterFirstHit = true;
				originalHit = new Point(height, width);
			}else {
				misses.add(new Point(height, width));
			}
			alreadyGuessed.add(new Point(height, width));
			return true;
		}
		// -------------  After First Hit  -------------
		else if(afterFirstHit) {
			int caseNum = (int)(Math.random() * 4);
			if(afterFirstHitCasesTried.size() == 4){
				caseNum = 4;
			}else {
				while(afterFirstHitCasesTried.contains(caseNum)) {
					caseNum = (int)(Math.random() * 4);
				}
			}
			switch (caseNum) {
			case 0:
				afterFirstHitCasesTried.add(caseNum);
				Point guess = new Point(originalHit.getHeight() + 1, originalHit.getWidth());
				if(alreadyGuessed.contains(guess) || guess.notValid()) {
					return false;
				}
				if(playerBoard.hitOrMiss(guess.getHeight(), guess.getWidth())) {
					afterFirstHit = false;
					afterSecondHit = true;
					secondHit = guess;
					afterFirstHitCasesTried.clear();
				}else {
					misses.add(guess);
					int regionNum = guess.getQuadrant();
					if (regionNum == 0) {
						regionZeroRange--;
					}else if(regionNum == 1) {
						regionOneRange--;
					}else if(regionNum == 2) {
						regionTwoRange--;
					}else {
						regionThreeRange--;
					}
				}
				alreadyGuessed.add(guess);
				break;
			case 1:
				afterFirstHitCasesTried.add(1);
				guess = new Point(originalHit.getHeight() - 1, originalHit.getWidth());
				if(alreadyGuessed.contains(guess) || guess.notValid()) {
					return false;
				}
				if(playerBoard.hitOrMiss(guess.getHeight(), guess.getWidth())) {
					afterFirstHit = false;
					afterSecondHit = true;
					secondHit = guess;
					afterFirstHitCasesTried.clear();
				}else {
					misses.add(guess);
					int regionNum = guess.getQuadrant();
					if (regionNum == 0) {
						regionZeroRange--;
					}else if(regionNum == 1) {
						regionOneRange--;
					}else if(regionNum == 2) {
						regionTwoRange--;
					}else {
						regionThreeRange--;
					}
				}
				alreadyGuessed.add(guess);
				break;
			case 2:
				afterFirstHitCasesTried.add(2);
				guess = new Point(originalHit.getHeight(), originalHit.getWidth() + 1);
				if(alreadyGuessed.contains(guess) || guess.notValid()) {
					return false;
				}
				if(playerBoard.hitOrMiss(guess.getHeight(), guess.getWidth())) {
					afterFirstHit = false;
					afterSecondHit = true;
					secondHit = guess;
					afterFirstHitCasesTried.clear();
				}else {
					misses.add(guess);
					int regionNum = guess.getQuadrant();
					if (regionNum == 0) {
						regionZeroRange--;
					}else if(regionNum == 1) {
						regionOneRange--;
					}else if(regionNum == 2) {
						regionTwoRange--;
					}else {
						regionThreeRange--;
					}
				}
				alreadyGuessed.add(guess);
				break;
			case 3:
				afterFirstHitCasesTried.add(3);
				guess = new Point(originalHit.getHeight(), originalHit.getWidth() - 1);
				if(alreadyGuessed.contains(guess) || guess.notValid()) {
					return false;
				}
				if(playerBoard.hitOrMiss(guess.getHeight(), guess.getWidth())) {
					afterFirstHit = false;
					afterSecondHit = true;
					secondHit = guess;
					afterFirstHitCasesTried.clear();
				}else {
					misses.add(guess);
					int regionNum = guess.getQuadrant();
					if (regionNum == 0) {
						regionZeroRange--;
					}else if(regionNum == 1) {
						regionOneRange--;
					}else if(regionNum == 2) {
						regionTwoRange--;
					}else {
						regionThreeRange--;
					}
				}
				alreadyGuessed.add(guess);
				break;
			case 4:
				randomGuess = true;
				afterFirstHit = false;
				afterSecondHit = false;
				afterThirdHit = false;
				afterFourthHit = false;
				afterFirstHitCasesTried.clear();
				return false;
			}
			if(playerBoard.aShipSunk()) {
				randomGuess = true;
				afterFirstHit = false;
				afterSecondHit = false;
				afterThirdHit = false;
				afterFourthHit = false;
				afterFirstHitCasesTried.clear();
			}
			return true;
		}
		// -------------  After Second Hit  -------------
		else if(afterSecondHit) {
			int caseNum = (int)(Math.random() * 2);
			if(afterSecondHitCasesTried.size() == 2){
				caseNum = 2;
			}else {
				while(afterSecondHitCasesTried.contains(caseNum)) {
					caseNum = (int)(Math.random() * 2);
				}
			}
			boolean sameHeight = false;
			if(secondHit.getHeight() == originalHit.getHeight()) {
				sameHeight = true;
			}
			if(sameHeight) {
				int height = secondHit.getHeight();
				int highWidth, lowWidth;
				if(originalHit.getWidth() > secondHit.getWidth()) {
					highWidth = originalHit.getWidth();
					lowWidth = secondHit.getWidth();
				}else {
					highWidth = secondHit.getWidth();
					lowWidth = originalHit.getWidth();
				}
				switch (caseNum) {
				case 0:
					afterSecondHitCasesTried.add(0);
					Point guess = new Point(height, highWidth + 1);
					if(alreadyGuessed.contains(guess) || guess.notValid()) {
						return false;
					}
					if(playerBoard.hitOrMiss(guess.getHeight(), guess.getWidth())) {
						afterSecondHit = false;
						afterThirdHit = true;
						thirdHit = guess;
						afterSecondHitCasesTried.clear();
					}else {
						misses.add(guess);
						int regionNum = guess.getQuadrant();
						if (regionNum == 0) {
							regionZeroRange--;
						}else if(regionNum == 1) {
							regionOneRange--;
						}else if(regionNum == 2) {
							regionTwoRange--;
						}else {
							regionThreeRange--;
						}
					}
					alreadyGuessed.add(guess);
					break;
				case 1:
					afterSecondHitCasesTried.add(1);
					guess = new Point(height, lowWidth - 1);
					if(alreadyGuessed.contains(guess) || guess.notValid()) {
						return false;
					}
					if(playerBoard.hitOrMiss(guess.getHeight(), guess.getWidth())) {
						afterSecondHit = false;
						afterThirdHit = true;
						thirdHit = guess;
						afterSecondHitCasesTried.clear();
					}else {
						misses.add(guess);
						int regionNum = guess.getQuadrant();
						if (regionNum == 0) {
							regionZeroRange--;
						}else if(regionNum == 1) {
							regionOneRange--;
						}else if(regionNum == 2) {
							regionTwoRange--;
						}else {
							regionThreeRange--;
						}
					}
					alreadyGuessed.add(guess);
					break;
				case 2:
					randomGuess = true;
					afterFirstHit = false;
					afterSecondHit = false;
					afterThirdHit = false;
					afterFourthHit = false;
					afterSecondHitCasesTried.clear();
					return false;
				}
				if(playerBoard.aShipSunk()) {
					randomGuess = true;
					afterFirstHit = false;
					afterSecondHit = false;
					afterThirdHit = false;
					afterFourthHit = false;
					afterSecondHitCasesTried.clear();
				}
				return true;
			}else {
				int width = secondHit.getWidth();
				int highHeight, lowHeight;
				if(originalHit.getHeight() > secondHit.getHeight()) {
					highHeight = originalHit.getHeight();
					lowHeight = secondHit.getHeight();
				}else {
					highHeight = secondHit.getHeight();
					lowHeight = originalHit.getHeight();
				}
				switch (caseNum) {
				case 0:
					afterSecondHitCasesTried.add(0);
					Point guess = new Point(highHeight + 1, width);
					if(alreadyGuessed.contains(guess) || guess.notValid()) {
						return false;
					}
					if(playerBoard.hitOrMiss(guess.getHeight(), guess.getWidth())) {
						afterSecondHit = false;
						afterThirdHit = true;
						thirdHit = guess;
						afterSecondHitCasesTried.clear();
					}else {
						misses.add(guess);
						int regionNum = guess.getQuadrant();
						if (regionNum == 0) {
							regionZeroRange--;
						}else if(regionNum == 1) {
							regionOneRange--;
						}else if(regionNum == 2) {
							regionTwoRange--;
						}else {
							regionThreeRange--;
						}
					}
					alreadyGuessed.add(guess);
					break;
				case 1:
					afterSecondHitCasesTried.add(1);
					guess = new Point(lowHeight - 1, width);
					if(alreadyGuessed.contains(guess) || guess.notValid()) {
						return false;
					}
					if(playerBoard.hitOrMiss(guess.getHeight(), guess.getWidth())) {
						afterSecondHit = false;
						afterThirdHit = true;
						thirdHit = guess;
						afterSecondHitCasesTried.clear();
					}else {
						misses.add(guess);
						int regionNum = guess.getQuadrant();
						if (regionNum == 0) {
							regionZeroRange--;
						}else if(regionNum == 1) {
							regionOneRange--;
						}else if(regionNum == 2) {
							regionTwoRange--;
						}else {
							regionThreeRange--;
						}
					}
					alreadyGuessed.add(guess);
					break;
				case 2:
					randomGuess = true;
					afterFirstHit = false;
					afterSecondHit = false;
					afterThirdHit = false;
					afterFourthHit = false;
					afterSecondHitCasesTried.clear();
					return false;
				}
				if(playerBoard.aShipSunk()) {
					randomGuess = true;
					afterFirstHit = false;
					afterSecondHit = false;
					afterThirdHit = false;
					afterFourthHit = false;
					afterSecondHitCasesTried.clear();
				}
				return true;
			}
		}
		// -------------  After Third Hit  -------------
		else if (afterThirdHit) {
			int caseNum = (int)(Math.random() * 2);
			if(afterThirdHitCasesTried.size() == 2){
				caseNum = 2;
			}else {
				while(afterThirdHitCasesTried.contains(caseNum)) {
					caseNum = (int)(Math.random() * 2);
				}
			}
			boolean sameHeight = false;
			if(thirdHit.getHeight() == originalHit.getHeight()) {
				sameHeight = true;
			}
			if(sameHeight) {
				int height = thirdHit.getHeight();
				int highWidth, lowWidth;
				if(originalHit.getWidth() > secondHit.getWidth() && originalHit.getWidth() > thirdHit.getWidth()) {
					highWidth = originalHit.getWidth();
					if(secondHit.getWidth() > thirdHit.getWidth()) {
						lowWidth = thirdHit.getWidth();
					}else {
						lowWidth = secondHit.getWidth();
					}
				}else if(secondHit.getWidth() > thirdHit.getWidth()){
					highWidth = secondHit.getWidth();
					if(thirdHit.getWidth() > originalHit.getWidth()) {
						lowWidth = originalHit.getWidth();
					}else {
						lowWidth = thirdHit.getWidth();
					}
				}else {
					highWidth = thirdHit.getWidth();
					if(originalHit.getWidth() > secondHit.getWidth()) {
						lowWidth = secondHit.getWidth();
					}else {
						lowWidth = originalHit.getWidth();
					}
				}
				switch (caseNum) {
				case 0:
					afterThirdHitCasesTried.add(0);
					Point guess = new Point(height, highWidth + 1);
					if(alreadyGuessed.contains(guess) || guess.notValid()) {
						return false;
					}
					if(playerBoard.hitOrMiss(guess.getHeight(), guess.getWidth())) {
						afterThirdHit = false;
						afterFourthHit = true;
						fourthHit = guess;
						afterThirdHitCasesTried.clear();
					}else {
						misses.add(guess);
						int regionNum = guess.getQuadrant();
						if (regionNum == 0) {
							regionZeroRange--;
						}else if(regionNum == 1) {
							regionOneRange--;
						}else if(regionNum == 2) {
							regionTwoRange--;
						}else {
							regionThreeRange--;
						}
					}
					alreadyGuessed.add(guess);
					break;
				case 1:
					afterThirdHitCasesTried.add(1);
					guess = new Point(height, lowWidth - 1);
					if(alreadyGuessed.contains(guess) || guess.notValid()) {
						return false;
					}
					if(playerBoard.hitOrMiss(guess.getHeight(), guess.getWidth())) {
						afterThirdHit = false;
						afterFourthHit = true;
						fourthHit = guess;
						afterThirdHitCasesTried.clear();
					}else {
						misses.add(guess);
						int regionNum = guess.getQuadrant();
						if (regionNum == 0) {
							regionZeroRange--;
						}else if(regionNum == 1) {
							regionOneRange--;
						}else if(regionNum == 2) {
							regionTwoRange--;
						}else {
							regionThreeRange--;
						}
					}
					alreadyGuessed.add(guess);
					break;
				case 2:
					randomGuess = true;
					afterFirstHit = false;
					afterSecondHit = false;
					afterThirdHit = false;
					afterFourthHit = false;
					afterThirdHitCasesTried.clear();
					return false;
				}
				if(playerBoard.aShipSunk()) {
					randomGuess = true;
					afterFirstHit = false;
					afterSecondHit = false;
					afterThirdHit = false;
					afterFourthHit = false;
					afterSecondHitCasesTried.clear();
				}
				return true;
			}else {
				int width = secondHit.getWidth();
				int highHeight, lowHeight;
				if(originalHit.getHeight() > secondHit.getHeight() && originalHit.getHeight() > thirdHit.getHeight()) {
					highHeight = originalHit.getHeight();
					if(secondHit.getHeight() > thirdHit.getHeight()) {
						lowHeight = thirdHit.getHeight();
					}else {
						lowHeight = secondHit.getHeight();
					}
				}else if(secondHit.getHeight() > thirdHit.getHeight()){
					highHeight = secondHit.getHeight();
					if(thirdHit.getHeight() > originalHit.getHeight()) {
						lowHeight = originalHit.getHeight();
					}else {
						lowHeight = thirdHit.getHeight();
					}
				}else {
					highHeight = thirdHit.getHeight();
					if(originalHit.getHeight() > secondHit.getHeight()) {
						lowHeight = secondHit.getHeight();
					}else {
						lowHeight = originalHit.getHeight();
					}
				}
				switch (caseNum) {
				case 0:
					afterThirdHitCasesTried.add(0);
					Point guess = new Point(highHeight + 1, width);
					if(alreadyGuessed.contains(guess) || guess.notValid()) {
						return false;
					}
					if(playerBoard.hitOrMiss(guess.getHeight(), guess.getWidth())) {
						afterThirdHit = false;
						afterFourthHit = true;
						fourthHit = guess;
						afterThirdHitCasesTried.clear();
					}else {
						misses.add(guess);
						int regionNum = guess.getQuadrant();
						if (regionNum == 0) {
							regionZeroRange--;
						}else if(regionNum == 1) {
							regionOneRange--;
						}else if(regionNum == 2) {
							regionTwoRange--;
						}else {
							regionThreeRange--;
						}
					}
					alreadyGuessed.add(guess);
					break;
				case 1:
					afterThirdHitCasesTried.add(1);
					guess = new Point(lowHeight - 1, width);
					if(alreadyGuessed.contains(guess) || guess.notValid()) {
						return false;
					}
					if(playerBoard.hitOrMiss(guess.getHeight(), guess.getWidth())) {
						afterThirdHit = false;
						afterFourthHit = true;
						fourthHit = guess;
						afterThirdHitCasesTried.clear();
					}else {
						misses.add(guess);
						int regionNum = guess.getQuadrant();
						if (regionNum == 0) {
							regionZeroRange--;
						}else if(regionNum == 1) {
							regionOneRange--;
						}else if(regionNum == 2) {
							regionTwoRange--;
						}else {
							regionThreeRange--;
						}
					}
					alreadyGuessed.add(guess);
					break;
				case 2:
					randomGuess = true;
					afterFirstHit = false;
					afterSecondHit = false;
					afterThirdHit = false;
					afterFourthHit = false;
					afterThirdHitCasesTried.clear();
					return false;
				}
				if(playerBoard.aShipSunk()) {
					randomGuess = true;
					afterFirstHit = false;
					afterSecondHit = false;
					afterThirdHit = false;
					afterFourthHit = false;
					afterSecondHitCasesTried.clear();
				}
				return true;
			}
		}
		// -------------  After Fourth Hit  -------------
		else if(afterFourthHit) {
			int caseNum = (int)(Math.random() * 2);
			if(afterFourthHitCasesTried.size() == 2){
				caseNum = 2;
			}else {
				while(afterFourthHitCasesTried.contains(caseNum)) {
					caseNum = (int)(Math.random() * 2);
				}
			}
			boolean sameHeight = false;
			if(fourthHit.getHeight() == originalHit.getHeight()) {
				sameHeight = true;
			}
			if(sameHeight) {
				int height = thirdHit.getHeight();
				int highWidth, lowWidth;
				if(originalHit.getWidth() > secondHit.getWidth() && originalHit.getWidth() > thirdHit.getWidth() && originalHit.getWidth() > fourthHit.getWidth()) {
					highWidth = originalHit.getWidth();
					if(secondHit.getWidth() < thirdHit.getWidth() && secondHit.getWidth() < fourthHit.getWidth()) {
						lowWidth = secondHit.getWidth();
					}else if(thirdHit.getWidth() < fourthHit.getWidth()){
						lowWidth = thirdHit.getWidth();
					}else {
						lowWidth = fourthHit.getWidth();
					}
				}else if(secondHit.getWidth() > thirdHit.getWidth() && secondHit.getWidth() > fourthHit.getWidth()){
					highWidth = secondHit.getWidth();
					if(originalHit.getWidth() < thirdHit.getWidth() && originalHit.getWidth() < fourthHit.getWidth()) {
						lowWidth = originalHit.getWidth();
					}else if(thirdHit.getWidth() < fourthHit.getWidth()){
						lowWidth = thirdHit.getWidth();
					}else {
						lowWidth = fourthHit.getWidth();
					}
				}else if(thirdHit.getWidth() > fourthHit.getWidth()){
					highWidth = thirdHit.getWidth();
					if(originalHit.getWidth() < secondHit.getWidth() && originalHit.getWidth() < fourthHit.getWidth()) {
						lowWidth = originalHit.getWidth();
					}else if(secondHit.getWidth() < fourthHit.getWidth()){
						lowWidth = secondHit.getWidth();
					}else {
						lowWidth = fourthHit.getWidth();
					}
				}else {
					highWidth = fourthHit.getWidth();
					if(originalHit.getWidth() < secondHit.getWidth() && originalHit.getWidth() < thirdHit.getWidth()) {
						lowWidth = originalHit.getWidth();
					}else if(secondHit.getWidth() < thirdHit.getWidth()){
						lowWidth = secondHit.getWidth();
					}else {
						lowWidth = thirdHit.getWidth();
					}
				}
				switch (caseNum) {
				case 0:
					afterFourthHitCasesTried.add(0);
					Point guess = new Point(height, highWidth + 1);
					if(alreadyGuessed.contains(guess) || guess.notValid()) {
						return false;
					}
					if(playerBoard.hitOrMiss(guess.getHeight(), guess.getWidth())) {
						randomGuess = true;
						afterFirstHit = false;
						afterSecondHit = false;
						afterThirdHit = false;
						afterFourthHit = false;
						afterFourthHitCasesTried.clear();
					}else {
						misses.add(guess);
						int regionNum = guess.getQuadrant();
						if (regionNum == 0) {
							regionZeroRange--;
						}else if(regionNum == 1) {
							regionOneRange--;
						}else if(regionNum == 2) {
							regionTwoRange--;
						}else {
							regionThreeRange--;
						}
					}
					alreadyGuessed.add(guess);
					break;
				case 1:
					afterFourthHitCasesTried.add(1);
					guess = new Point(height, lowWidth - 1);
					if(alreadyGuessed.contains(guess) || guess.notValid()) {
						return false;
					}
					if(playerBoard.hitOrMiss(guess.getHeight(), guess.getWidth())) {
						randomGuess = true;
						afterFirstHit = false;
						afterSecondHit = false;
						afterThirdHit = false;
						afterFourthHit = false;
						afterFourthHitCasesTried.clear();
					}else {
						misses.add(guess);
						int regionNum = guess.getQuadrant();
						if (regionNum == 0) {
							regionZeroRange--;
						}else if(regionNum == 1) {
							regionOneRange--;
						}else if(regionNum == 2) {
							regionTwoRange--;
						}else {
							regionThreeRange--;
						}
					}
					alreadyGuessed.add(guess);
					break;
				case 2:
					randomGuess = true;
					afterFirstHit = false;
					afterSecondHit = false;
					afterThirdHit = false;
					afterFourthHit = false;
					afterFourthHitCasesTried.clear();
					return false;
				}
				if(playerBoard.aShipSunk()) {
					randomGuess = true;
					afterFirstHit = false;
					afterSecondHit = false;
					afterThirdHit = false;
					afterFourthHit = false;
					afterFourthHitCasesTried.clear();
				}
				return true;
			}else {
				int width = secondHit.getWidth();
				int highHeight, lowHeight;
				if(originalHit.getHeight() > secondHit.getHeight() && originalHit.getHeight() > thirdHit.getHeight() && originalHit.getHeight() > fourthHit.getHeight()) {
					highHeight = originalHit.getHeight();
					if(secondHit.getHeight() < thirdHit.getHeight() && secondHit.getHeight() < fourthHit.getHeight()) {
						lowHeight = secondHit.getHeight();
					}else if(thirdHit.getHeight() < fourthHit.getHeight()){
						lowHeight = thirdHit.getHeight();
					}else {
						lowHeight = fourthHit.getHeight();
					}
				}else if(secondHit.getHeight() > thirdHit.getHeight() && secondHit.getHeight() > fourthHit.getHeight()){
					highHeight = secondHit.getHeight();
					if(originalHit.getHeight() < thirdHit.getHeight() && originalHit.getHeight() < fourthHit.getHeight()) {
						lowHeight = originalHit.getHeight();
					}else if(thirdHit.getHeight() < fourthHit.getHeight()){
						lowHeight = thirdHit.getHeight();
					}else {
						lowHeight = fourthHit.getHeight();
					}
				}else if(thirdHit.getHeight() > fourthHit.getHeight()){
					highHeight = thirdHit.getHeight();
					if(originalHit.getHeight() < secondHit.getHeight() && originalHit.getHeight() < fourthHit.getHeight()) {
						lowHeight = originalHit.getHeight();
					}else if(secondHit.getHeight() < fourthHit.getHeight()){
						lowHeight = secondHit.getHeight();
					}else {
						lowHeight = fourthHit.getHeight();
					}
				}else {
					highHeight = fourthHit.getHeight();
					if(originalHit.getHeight() < secondHit.getHeight() && originalHit.getHeight() < thirdHit.getHeight()) {
						lowHeight = originalHit.getHeight();
					}else if(secondHit.getHeight() < thirdHit.getHeight()){
						lowHeight = secondHit.getHeight();
					}else {
						lowHeight = thirdHit.getHeight();
					}
				}
				switch (caseNum) {
				case 0:
					afterFourthHitCasesTried.add(0);
					Point guess = new Point(highHeight + 1, width);
					if(alreadyGuessed.contains(guess) || guess.notValid()) {
						return false;
					}
					if(playerBoard.hitOrMiss(guess.getHeight(), guess.getWidth())) {
						randomGuess = true;
						afterFirstHit = false;
						afterSecondHit = false;
						afterThirdHit = false;
						afterFourthHit = false;
						afterFourthHitCasesTried.clear();
					}else {
						misses.add(guess);
						int regionNum = guess.getQuadrant();
						if (regionNum == 0) {
							regionZeroRange--;
						}else if(regionNum == 1) {
							regionOneRange--;
						}else if(regionNum == 2) {
							regionTwoRange--;
						}else {
							regionThreeRange--;
						}
					}
					alreadyGuessed.add(guess);
					break;
				case 1:
					afterFourthHitCasesTried.add(1);
					guess = new Point(lowHeight - 1, width);
					if(alreadyGuessed.contains(guess) || guess.notValid()) {
						return false;
					}
					if(playerBoard.hitOrMiss(guess.getHeight(), guess.getWidth())) {
						randomGuess = true;
						afterFirstHit = false;
						afterSecondHit = false;
						afterThirdHit = false;
						afterFourthHit = false;
						afterFourthHitCasesTried.clear();
					}else {
						misses.add(guess);
						int regionNum = guess.getQuadrant();
						if (regionNum == 0) {
							regionZeroRange--;
						}else if(regionNum == 1) {
							regionOneRange--;
						}else if(regionNum == 2) {
							regionTwoRange--;
						}else {
							regionThreeRange--;
						}
					}
					alreadyGuessed.add(guess);
					break;
				case 2:
					randomGuess = true;
					afterFirstHit = false;
					afterSecondHit = false;
					afterThirdHit = false;
					afterFourthHit = false;
					afterFourthHitCasesTried.clear();
					return false;
				}
				if(playerBoard.aShipSunk()) {
					randomGuess = true;
					afterFirstHit = false;
					afterSecondHit = false;
					afterThirdHit = false;
					afterFourthHit = false;
					afterFourthHitCasesTried.clear();
				}
				return true;
			}
		}
		return true;
	}
	
	/*public static void main(String [] args) {
		Scanner reader = new Scanner(System.in);
		AIBoard board = new AIBoard();
		PlayerBoard a = new PlayerBoard(board.getBoard());
		AI ai = new AI(a);
		long start = System.currentTimeMillis();
		while(!a.checkIfLost()) {
			while(!ai.guess()) {
			}
			System.out.println(ai.playerBoard);
			//reader.nextLine();
		}
		long end = System.currentTimeMillis();
		System.out.println("Time: " + (end - start));
	}*/
}
