package de.dualuse.util;

import java.util.Arrays;

public class AllocationTable {
	
	private int[] blocks;
	private int[] files;
	
	private int fileCount = 0;
	private int blockCount = 0;
	
	private int freeFile;
	private int freeBlock;
	
	public AllocationTable() {
		this(1024,256);
	}
	
	public AllocationTable(int initialBlockCapacity, int initialFileCapacity) {
		
		this.blocks = new int[initialBlockCapacity];
		this.files = new int[initialFileCapacity];
		
		this.freeBlock = 0;
		this.freeFile = 0;
		
	}
	
	
	public int create() {
		if (fileCount==files.length) 
			files = Arrays.copyOf(files, files.length*3/2);
		
		int newFile = freeFile;
		freeFile = this.files[freeFile]+(freeFile+1);
		
		this.files[newFile] = -1;
		
		fileCount++;
		return newFile;
	}
	
	
	public int grow(int file) {
		if (blockCount+1==blocks.length) 
			blocks = Arrays.copyOf(blocks, blocks.length*3/2);

		int newBlock = freeBlock;
		
		freeBlock = this.blocks[freeBlock]+(freeBlock+1);
		
		this.blocks[newBlock] = this.files[file];
		this.files[file] = newBlock;
		
		blockCount++;
		return newBlock;
	}
	
	public boolean isEmpty(int file) {
		return this.files[file] == -1; 
	}
	
	public int trim(int file) {
		int newFree = this.files[file];
		
		this.files[file] = this.blocks[ newFree ];
		
		this.blocks[ newFree ] = this.freeBlock-(newFree+1);
		this.freeBlock = newFree;
		
		return newFree;
	}
	
	public void delete(int file) {
		while (!isEmpty(file))
			trim(file);
		
		dispose(file);
	}
	
	public boolean dispose(int file) {
		if (this.files[ file ]!=-1)
			return false;
			
		this.files[ file ] = freeFile-(file+1);
		
		freeFile = file;

		fileCount--;
		
		return true;
	}

	public int size() { 
		return fileCount; 
	}
	
	public int length(int file) {
		int counter =0 ;
		for (int head=this.files[ file ]; head!=-1; head = this.blocks[head])
			counter++;
		
		return counter;
	}
	
	public int[] list(int file) { return list(file, new int[length(file)]); }
	public int[] list(int file, int[] storage) { return list(file,storage,0,storage.length); }
	public int[] list(int file, int[] storage, int offset, int length) {
		int counter =0 ;
		for (int head=this.files[ file ]; head!=-1 && length>0; head = this.blocks[head], length--)
			storage[offset+counter++] = head;
		
		return storage;
	}
	
	
	////////////////
	
	public static void main(String[] args) {
		
		AllocationTable at = new AllocationTable(10, 4);
		
		int first = at.create();
		System.out.println(Arrays.toString(at.files)+"   "+at.freeFile);
		int second = at.create();
		System.out.println(Arrays.toString(at.files)+"   "+at.freeFile);
		at.create();
		System.out.println(Arrays.toString(at.files)+"   "+at.freeFile);
		at.create();


		System.out.println(Arrays.toString(at.files)+"   "+at.freeFile);
		
		at.delete(second);

		System.out.println(Arrays.toString(at.files)+"   "+at.freeFile);
		
		at.create();
		at.create();
		at.create();

		System.out.println(Arrays.toString(at.files)+"   "+at.freeFile);
		
		
		if (true)
			return;
		
		int f = at.create();
		int g = at.create();
		int h = at.create();
		
		System.out.println(Arrays.toString(at.files)+"   "+at.freeFile);
		System.out.println(Arrays.toString(at.blocks)+"   "+at.freeBlock);
		
//		
		at.grow(f);
		at.grow(f);
		at.grow(f);

		System.out.println();
		System.out.println(Arrays.toString(at.files)+"   "+at.freeFile);
		System.out.println(Arrays.toString(at.blocks)+"   "+at.freeBlock);
		

		at.grow(g);
		at.grow(h);
		at.grow(g);
		at.grow(h);
		
		System.out.println();
		System.out.println(Arrays.toString(at.files)+"   "+at.freeFile);
		System.out.println(Arrays.toString(at.blocks)+"   "+at.freeBlock);
		
		
		at.trim(g);
		
		System.out.println();
		System.out.println(Arrays.toString(at.files)+"   "+at.freeFile);
		System.out.println(Arrays.toString(at.blocks)+"   "+at.freeBlock);
		
		at.grow(h);
		
		System.out.println();
		System.out.println(Arrays.toString(at.files)+"   "+at.freeFile);
		System.out.println(Arrays.toString(at.blocks)+"   "+at.freeBlock);
		
		System.out.println("---------------");
		
		at.trim(h);
		at.trim(h);
		at.trim(h);
		at.delete(h);
		
		System.out.println();
		System.out.println(Arrays.toString(at.files)+"   "+at.freeFile);
		System.out.println(Arrays.toString(at.blocks)+"   "+at.freeBlock);
		
		System.out.println();
		System.out.println(at.length(f)+": "+Arrays.toString(at.list(f)) );
		System.out.println(at.length(g)+": "+Arrays.toString(at.list(g)) );
		
		System.out.println(at.length(h)+": "+Arrays.toString(at.list(h)) );
		
		System.out.println( at.isEmpty(g) );
		System.out.println( at.trim(g) );
		System.out.println( at.isEmpty(g) );
		
	}
	
}


