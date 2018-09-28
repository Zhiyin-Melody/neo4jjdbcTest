package TransTest;

/**
 * @Author:lizhiyin
 * @Desciption:描述一个用邻接表构建的图结构的类定义，包括链表结构和顶点结构定义；
 * @para:ChildTreeNode类和TreeNode类；
 * @Date:Created in 10:35 2018/4/18
 */
public class Sparql2CypherGraph {

    public static void main(String[] args) {

    }
}
//链表结构；
class ChildTreeNode{
    int childIndex;
    ChildTreeNode pNext;
    String relName;


    public ChildTreeNode(int childIndex, String relName){
        this.childIndex = childIndex;
        this.pNext = null;
        this.relName = relName;
    }
}
//顶点数组；
class TreeNode{
    int idx;
    String data;
    ChildTreeNode pChildNext;
    boolean isVisited;
    boolean isRel;

    public TreeNode(int idx, String data) {
        this.idx = idx;
        this.data = data;
        this.pChildNext = null;
        this.isVisited = false;
        this.isRel = true;
    }
}
