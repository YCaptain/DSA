
import dsa.iface.IBinaryTree;
import dsa.iface.INode;

public class BinaryTreeComparer<T extends Comparable<T>> {
   private IBinaryTree<T> t1;
   private IBinaryTree<T> t2;

   public BinaryTreeComparer( IBinaryTree<T> t1, IBinaryTree<T> t2 ) {
      this.t1 = t1;
      this.t2 = t2;
   }

   /**
    * Test to see if two Binary Trees are equal (have the same structure and the same contents)
    * @return
    */
   public boolean areEqual() {
	   return false; // <-- this is just a placeholder: remove it from your final code.
   }

}
