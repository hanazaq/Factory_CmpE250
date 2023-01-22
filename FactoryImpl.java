import java.util.NoSuchElementException;

//the way they are mentioned in the java doc
//free to add helper methods
class FactoryImpl implements Factory {
	// will be modified by overridden methods
	private Holder first;
	private Holder last;
	private Integer size = 0;

	@Override
	public void addFirst(Product product) {
		// what if there is a problem with the product?
		if (this.first == null) {
			// adding to empty linked list
			this.last = new Holder(null, product, null);
			this.first = this.last;
			this.size++;
		} else {
			Holder old = this.first;
			Holder new_holder = new Holder(null, product, old);
			old.setPreviousHolder(new_holder);
			this.first = new_holder;
			this.size++;
		}

	}

	@Override
	public void addLast(Product product) {
		if (this.last == null) {
			// add to empty linked list
			this.first = new Holder(null, product, null);
			this.last = this.first;
			size++;
		} else {
			Holder old = this.last;
			Holder new_holder = new Holder(old, product, null);
			old.setNextHolder(new_holder);
			this.last = new_holder;
			size++;
		}
	}

	private void remove_holder(Holder holder) {
		if (holder != null) {
			// linked list has only one element
			if (holder.getNextHolder() == null && holder.getPreviousHolder() == null) {
				this.first = null;
				this.last = null;
				this.size = 0;
			}
			// remove from head
			else if (holder.getPreviousHolder() == null) {
				Holder new_first = this.first.getNextHolder();
				this.first = new_first;
				new_first.setPreviousHolder(null);
				this.size--;
			}
			// remove from tail
			else if (holder.getNextHolder() == null) {
				Holder new_last = this.last.getPreviousHolder();
				this.last = new_last;
				new_last.setNextHolder(null);
				this.size--;
			}
			// remove from the middle
			else {
				Holder new_prev = holder.getPreviousHolder();
				Holder new_next = holder.getNextHolder();
				new_prev.setNextHolder(new_next);
				new_next.setPreviousHolder(new_prev);
				this.size--;
			}
		} else {
			throw new NoSuchElementException();
		}

	}

	@Override
	public Product removeFirst() throws NoSuchElementException {
		if (this.first == null) {
			// empty linked list
			throw new NoSuchElementException();
		}
		Product to_return = this.first.getProduct();
		this.remove_holder(this.first);
		return to_return;
	}

	@Override
	public Product removeLast() throws NoSuchElementException {
		if (this.last == null) {
			// empty linked list
			throw new NoSuchElementException();
		}
		Product to_return = this.last.getProduct();
		this.remove_holder(this.last);
		return to_return;
	}

	@Override
	public Product find(int id) throws NoSuchElementException {

		Product found = null;
		Holder curr = this.first;
		while (curr != null) {
			if (curr.getProduct().getId() == id) {
				found = curr.getProduct();
				break;
			}
			curr = curr.getNextHolder();
		}
		if (found == null) {
			throw new NoSuchElementException();
		}
		return found;
	}

	@Override
	public Product update(int id, Integer value) throws NoSuchElementException {
		Product to_update = this.find(id); // if the id is not found, Exception will be thrown from find() method
		Product keep_copy = new Product(to_update.getId(), to_update.getValue());
		to_update.setValue(value);
		return keep_copy;
	}

	private Holder get_holder(int index) throws IndexOutOfBoundsException {

		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		Holder result = null;
		if (index <= size / 2) {
			int position = 0;
			Holder curr = this.first;
			while (position != index) {
				curr = curr.getNextHolder();
				position++;
			}
			result = curr;
		} else {
			int position = size - 1;
			Holder curr = this.last;
			while (position != index) {
				curr = curr.getPreviousHolder();
				position--;
			}
			result = curr;
		}
		return result;

	}

	@Override
	public Product get(int index) throws IndexOutOfBoundsException {
		return this.get_holder(index).getProduct();
	}

	@Override
	public void add(int index, Product product) throws IndexOutOfBoundsException {

		// this add method depends of the holders not product...
		// can the index be equal to the size?
		if (index < 0 || index > this.size) {
			throw new IndexOutOfBoundsException();
		}

		// adding to head
		if (index == 0) {
			this.addFirst(product);
		}
		// adding to the tail
		else if (index == this.size) {
			this.addLast(product);

		}
		// adding to the middle
		else {
			Holder curr = this.get_holder(index - 1);
			Holder new_prev = curr;
			Holder new_next = curr.getNextHolder();
			Holder new_holder = new Holder(new_prev, product, new_next);
			new_prev.setNextHolder(new_holder);
			new_next.setPreviousHolder(new_holder);
			this.size++;
		}

	}

	@Override
	public Product removeIndex(int index) throws IndexOutOfBoundsException {
		if (this.size == 0 || this.first == null || index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		Holder result = this.get_holder(index);
		Product to_return = result.getProduct();
		this.remove_holder(result);
		return to_return;
	}

	@Override
	public Product removeProduct(int value) throws NoSuchElementException {

		if (this.size == 0 || this.first == null) {
			throw new NoSuchElementException();
		}
		Holder curr = this.first;
		int index = 0;
		while (value != curr.getProduct().getValue()) {
			if (index == this.size - 1) {
				break;
			}
			curr = curr.getNextHolder();
			index++;

		}
		if (value != curr.getProduct().getValue()) {
			throw new NoSuchElementException();
		}
		Holder result = curr;
		Product to_return = result.getProduct();
		this.remove_holder(result);
		return to_return;
	}

	@Override
	public int filterDuplicates() {
		if (this.first == null || this.size <= 1) {
			return 0;
		}
		int counter = 0;
		Holder curr = this.first;
		while (curr != null && curr.getNextHolder() != null) {

			Holder compare = curr.getNextHolder();
			while (compare != null) {
				if (curr.getProduct().getValue() == compare.getProduct().getValue()) {
					this.remove_holder(compare);
					counter++;
				}
				compare = compare.getNextHolder();

			}

			curr = curr.getNextHolder();

		}
		return counter;
	}

	// O(N**2)
//	public void reverse2() {
//		if (this.size > 1) {
//			for (int i = 0; i < this.size - 1; i++) {
//				Product removed = this.last.getProduct();
//				this.removeLast();
//				this.add(i, removed);
//			}
//		}
//	}

	private void copy(FactoryImpl f) {
		this.first = null;
		this.last = null;
		Holder curr = f.first;
		while (curr != null) {
			this.addFirst(curr.getProduct());
			curr = curr.getNextHolder();
		}
	}

	// O(N)
	@Override
	public void reverse() {
		if (this.size > 1) {
			FactoryImpl reveresed = new FactoryImpl();
			while (this.size > 0) {
				Product removed = this.last.getProduct();
				this.removeLast();
				reveresed.addFirst(removed);
			}
			this.copy(reveresed);
		}

	}

	public String print() {
		String result = "{";
		Holder curr = this.first;
		while (curr != null) {
			result += curr.toString();
			if (curr.getNextHolder() != null)
				result += ",";
			curr = curr.getNextHolder();
		}
		result += "}";
		return result;
//		System.out.println("size is " + this.size);

	}
}