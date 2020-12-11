package ro.ubbcluj.domain.Symbol;

import java.util.ArrayList;

public class Node {
    private String data;
    private int id;
    private Node left = null;
    private Node right = null;
    private SymbolTable bst;

    public Node(String data, int id, SymbolTable bst) {
        this.data = data;
        this.id = id;
        this.bst = bst;
    }

    public Boolean insert(String data){
        if (this.data.equals(data)){
            return false;
        }
        else if (data.compareTo(this.data) < 0){
            if (this.left != null){
                return this.left.insert(data);
            }
            else{
                this.left = new Node(data, bst.id, bst);
                bst.id += 1;
                return true;
            }
        }
        else{
            if (this.right != null){
                return this.right.insert(data);
            }
            else{
                this.right = new Node(data, bst.id, bst);
                bst.id += 1;
                return true;
            }
        }
    }

    public int search(String data){
        if (this.data.equals(data)){
            return this.id;
        }
        else if (data.compareTo(this.data) < 0 && this.left != null){
            return this.left.search(data);
        }
        else if (data.compareTo(this.data) > 0 && this.right != null){
            return this.right.search(data);
        }
        return -1;
    }

    public String searchById(int id){
        String result = "";
        if (this.id == id){
            return this.data;
        }
        else{
            if (this.left != null){
                result += this.left.searchById(id);
            }
            if (this.right != null){
                result += this.right.searchById(id);
            }
        }
        return result;
    }

    public ArrayList<String> getInorder (ArrayList<String> listToReturn){
        if (this.left != null){
            this.left.getInorder(listToReturn);
        }
        listToReturn.add(this.data + ": " + this.id);
        if (this.right != null){
            this.right.getInorder(listToReturn);
        }
        return listToReturn;
    }
}