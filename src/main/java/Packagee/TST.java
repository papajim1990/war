/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Packagee;

/**
 *
 * @author user1
 */
public class TST {
	private Node root;

	public TST (){
		root = null;
	}

	public TST(Node node){
		root = node;
	}		

	public Node getRoot(){
		return root;
	}

	public void setRoot(Node node){
		this.root = node;
	}
	public void insert(String word, String frequency){
		root = insert(root, word, frequency, 0);
	}

	public Node insert (Node node, String word, String frequency, int pos){
		if(node == null){
			if(word.length() <= pos){
				return node;
			}

			node = new Node(word.charAt(pos));
			if(pos+1 == word.length()){
				node.setIsEnd(true);
				node.setFrequency(frequency);
				return node;
			}
		}

		if(word.charAt(pos) < node.getData()){
			node.setLeft(insert(node.getLeft(), word, frequency, pos));
		}else if (word.charAt(pos) > node.getData()) {
			node.setRight(insert(node.getRight(), word, frequency, pos));
		}else {
			if(pos+1 < word.length()){
				node.setEqual(insert(node.getEqual(), word, frequency, pos+1));
			}else {
				// we encountered an end of a word
				node.setIsEnd(true);
				node.setFrequency(frequency);
			}
		}
		return  node;
	}

	public boolean search(Node node, String word, int pos){
	
		while (node != null ) {
			if(word.charAt(pos) < node.getData()){
				node = node.getLeft();
			}else if (word.charAt(pos) > node.getData()){
				node = node.getRight();
			}else {
				if(node.getIsEnd()==true && pos == word.length()-1){
					return true;
				}
				pos++;
				node = node.getEqual();
			}
		}
		return false;
	}	// search()
}
