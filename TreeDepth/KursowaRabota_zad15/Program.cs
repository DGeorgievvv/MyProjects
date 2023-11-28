using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace KursowaRabota_zad15
{
    public class Node
    {
        public int data;
        public Node left, right;

        public Node(int item)
        {
            data = item;
            left = null;
            right = null;
        }
    }

    public class BinaryTree
    {
        public Node root;

        private int FindTreeDepth(Node node)
        {
            if(node == null)
            {
                return 0;
            }
            else
            {
                int leftDepth = FindTreeDepth(node.left);
                int rightDepth = FindTreeDepth(node.right);
                if(leftDepth >= rightDepth)
                {
                    return leftDepth + 1;
                }
                else
                {
                    return rightDepth + 1;
                }
            }
        }

        public int TreeDepth()
        {
            return FindTreeDepth(root);
        }
    }
    class Program
    {
        static void Main(string[] args)
        {
            BinaryTree tree = new BinaryTree();
            tree.root = new Node(1);
            tree.root.left = new Node(2);
            tree.root.right = new Node(3);
            tree.root.left.left = new Node(4);
            tree.root.left.right = new Node(5);

            int depth = tree.TreeDepth();
            Console.WriteLine($"Tree depth is : {depth}");
        }
    }
}
