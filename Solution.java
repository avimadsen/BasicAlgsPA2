import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Solution {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		int numOfInquiries = sc.nextInt();
		TwoThreeTree planetTree= new TwoThreeTree();
		int typeOfInquiry;

		for(int i=0; i< numOfInquiries; i++)
		{		
			typeOfInquiry = sc.nextInt();
			if(typeOfInquiry == 1)
			{
				//System.out.println("worked 1");
				String planetName= sc.next();
				int planetFee =sc.nextInt();
				twothree.insert(planetName, planetFee,planetTree);

			}
			if(typeOfInquiry == 2)
			{			
				//System.out.println("worked 2");

				String planetNameA = sc.next();
				String planetNameB = sc.next();
				int planetFeeIncrease= sc.nextInt();
				addRange(planetNameA, planetNameB, planetTree, planetFeeIncrease);
			}
			if(typeOfInquiry == 3)
			{				
				//		System.out.println("worked 3");
				String planetName = sc.next();
				searchForPlanet(planetName, planetTree);
			}

		}
	}
	public static void addAll(Node p, int h,int delta)
	{

		p.value = p.value + delta; 
		/*
		if(h!=0)
		{
			InternalNode i = (InternalNode) p;
			if(i.child2 == null)
			{
				i.child0.value = i.child0.value +delta;
				i.child1.value = i.child1.value + delta;
			}
			if(i.child2 != null)
			{
				i.child0.value = i.child0.value +delta;
				i.child1.value = i.child1.value +delta;
				i.child2.value = i.child2.value +delta;
			}
		}
		else
		{
			LeafNode l = (LeafNode)p;
			l.value = l.value + delta;
		}
		 */
	}




	//formerly printRange
	static void addRange(String x, String y, TwoThreeTree planetTree, int delta)
	{

		/*	if(isInTree(x, planetTree) == null || isInTree(y,planetTree) == null)
		{
			return;
		}
		 */
		// if database is empty
		if(planetTree.root==null)
		{


			return;
		}
		//set x as the lower string
		Node[] pathToX = null;
		Node[] pathToY=null;
		if(x.compareTo(y)>0)
		{
			String temp = y;
			y=x;
			x=temp;
		}

		//return node[] using the search method
		pathToX = search(x, planetTree );
		pathToY = search(y, planetTree);


		//find the divergence point, should be the same for both node[]
		int divergence =0;

		for(int i =0; i<pathToX.length; i++)
		{
			if(pathToX[i] == pathToY[i])
			{
				divergence =i;
			}
		}
		/*
		//if twoThreeTree has 1-3 nodes
		if(planetTree.height==1)
		{
			//System.out.println("here");

			pathToX = search(x, planetTree);
			pathToY = search(y, planetTree);

			//if both searches ended at x
			if(pathToX[pathToX.length-1] == pathToY[pathToY.length-1])
			{
				addAll(pathToX[pathToX.length-1],1, delta);
				return;
			}

			//if x and y are the same and the root of the tree
			if(planetTree.root.guide.equals(x) && planetTree.root.guide.equals(y))
			{

				addAll(planetTree.root,0,delta);
			}
			// if twoThreeTree has a child2 but child2 isnt y
			else if(((InternalNode)planetTree.root).child2 != null && pathToY[1] != ((InternalNode)planetTree.root).child2)
			{
				addAll(((InternalNode)planetTree.root).child0,0,delta);
				addAll(((InternalNode)planetTree.root).child1,0,delta);
			}
			else if( ((InternalNode)planetTree.root).child2 ==null)
			{
				addAll(((InternalNode)planetTree.root).child0,0,delta);
				addAll(((InternalNode)planetTree.root).child1,0,delta);

			}
			// if child 1 is x and child 2 is y
			else if(pathToX[1] != ((InternalNode)planetTree.root).child0)
			{
				addAll(((InternalNode)planetTree.root).child1,0,delta);
				addAll(((InternalNode)planetTree.root).child2,0,delta);
			}

			// print the whole tree
			else
			{  
				addAll(planetTree.root,0,delta);
			}


		}

		else 
		{  */ 
		//if no divergence check if that leaf node is within the range


		if(pathToX[pathToX.length-1] == pathToY[pathToY.length-1])
		{
			if(pathToX[pathToX.length-1].guide.compareTo(x) >=0 && pathToX[pathToX.length-1].guide.compareTo(y) <=0)
			{
			
				
				pathToX[pathToX.length-1].value = pathToX[pathToX.length-1].value + delta;
			}

			return;
		}
		if(pathToX[pathToX.length-1].guide.compareTo(x) >= 0)
		{

			//print x
			/*
				try {
					writer.write(pathToX[pathToX.length-1].guide + " "+ ((LeafNode)pathToX[pathToX.length-1]).value + "\n");
				} catch (IOException e) {
					e.printStackTrace(); 
				} 
			 */
			addAll(pathToX[pathToX.length-1], 1, delta);
			//pathToX[pathToX.length-1].value = pathToX[pathToX.length-1].value + delta;

		}
		//walk back along pathToX
		for(int i = pathToX.length-2; i>=divergence; i--)
		{
			//if for loop hit the divergence and there is a middle node, printAll the middle node
			if(i == divergence)
			{            

				if(pathToX[i+1] == ((InternalNode)pathToX[i]).child0 && ((InternalNode)pathToX[i]).child2 == pathToY[divergence+1] && ((InternalNode)pathToX[i]).child1 != null )
				{
					addAll(((InternalNode)pathToX[i]).child1,1, delta );
				}
			}
			else if(((InternalNode)pathToX[i]).child2 != null && ((InternalNode)pathToX[i]).child0 == pathToX[i+1] && i> divergence)
			{
				addAll(((InternalNode)pathToX[i]).child1,1,delta );
				addAll(((InternalNode)pathToX[i]).child2,1,delta ); 
			}
			else if(((InternalNode)pathToX[i]).child2 != null && ((InternalNode)pathToX[i]).child1 == pathToX[i+1]&& i> divergence)
			{
				addAll(((InternalNode)pathToX[i]).child2,1,delta );
			}
			else if(((InternalNode)pathToX[i]).child2 == null && ((InternalNode)pathToX[i]).child0 == pathToX[i+1]&& i> divergence)
			{    
				addAll(((InternalNode)pathToX[i]).child1,1,delta );
			}


		}
		for(int i = divergence+1; i<pathToY.length-1; i++)
		{

			if(((InternalNode)pathToY[i]).child2 == pathToY[i+1])
			{

				addAll(((InternalNode)pathToY[i]).child0,1,delta);
				addAll(((InternalNode)pathToY[i]).child1,1,delta);

			}
			else if(((InternalNode)pathToY[i]).child1 == pathToY[i+1])
			{
				addAll(((InternalNode)pathToY[i]).child0,1,delta);

			}




		}            
		if(pathToY[pathToY.length-1].guide.compareTo(y) <= 0)
		{
			/*
				try {
					writer.write(pathToY[pathToY.length-1].guide + " " + ((LeafNode)pathToY[pathToY.length-1]).value + "\n");
				} catch (IOException e) {
					e.printStackTrace(); 
				} 
			 */
			addAll(pathToY[pathToY.length-1], 1,delta);
			//pathToY[pathToY.length-1].value = pathToY[pathToY.length-1].value + delta;

		}
	}
	//}


	public static void searchForPlanet(String x, TwoThreeTree searchTree)
	{

		Node searchNode = searchTree.root;
		int height = searchTree.height;
		int fee=0;
		for(int i =0; i<height; i++)
		{
			if(x.compareTo(((InternalNode)searchNode).child0.guide) <=0)
			{
				fee = fee + searchNode.value;
				searchNode = ((InternalNode)searchNode).child0;
			}
			else if(((InternalNode)searchNode).child2 == null || x.compareTo(((InternalNode)searchNode).child1.guide) <= 0)
			{
				fee = fee+ searchNode.value;
				searchNode = ((InternalNode)searchNode).child1;
			}

			else
			{
				fee = fee + searchNode.value;
				searchNode = ((InternalNode)searchNode).child2;
			}	
		}
		if(x.equals(searchNode.guide))
			System.out.println((searchNode.value +fee));
		else
			System.out.println("-1");
	}


	public static Node[] search(String x, TwoThreeTree searchTree)
	{
		Node[] path = new Node[searchTree.height+1];
		Node root = searchTree.root;
		int height = searchTree.height;
		Node searchNode = root;
		for(int i =0; i<height; i++)
		{
			if(x.compareTo(((InternalNode)searchNode).child0.guide) <=0)
			{
				path[i] = searchNode;
				searchNode = ((InternalNode)searchNode).child0;
			}
			else if(((InternalNode)searchNode).child2 == null || x.compareTo(((InternalNode)searchNode).child1.guide) <= 0)
			{
				path[i] = searchNode;
				searchNode = ((InternalNode)searchNode).child1;
			}

			else
			{
				path[i]=searchNode;
				searchNode = ((InternalNode)searchNode).child2;
			}
		}
		path[height] = searchNode;
		return path;
	}
	public static Node[] isInTree(String x, TwoThreeTree searchTree)
	{
		Node[] path = new Node[searchTree.height+1];
		Node root = searchTree.root;
		int height = searchTree.height;
		Node searchNode = root;
		for(int i =0; i<height; i++)
		{
			if(x.compareTo(((InternalNode)searchNode).child0.guide) <=0)
			{
				path[i] = searchNode;
				searchNode = ((InternalNode)searchNode).child0;
			}
			else if(((InternalNode)searchNode).child2 == null || x.compareTo(((InternalNode)searchNode).child1.guide) <= 0)
			{
				path[i] = searchNode;
				searchNode = ((InternalNode)searchNode).child1;
			}

			else
			{
				path[i]=searchNode;
				searchNode = ((InternalNode)searchNode).child2;
			}
		}
		if(searchNode.guide.equals(x))
		{
			path[height] = searchNode;
			return path;
		}
		else
			return null;
	}

}


