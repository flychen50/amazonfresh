// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.common.collect;

import com.google.common.base.Function;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;

// Referenced classes of package com.google.common.collect:
//            AbstractMapEntry

final class CustomConcurrentHashMap
{
    static final class Builder
    {

        public ConcurrentMap buildComputingMap(ComputingStrategy computingstrategy, Function function)
        {
            if(computingstrategy == null)
                throw new NullPointerException("strategy");
            if(function == null)
                throw new NullPointerException("computer");
            else
                return new ComputingImpl(computingstrategy, this, function);
        }

        public ConcurrentMap buildMap(Strategy strategy)
        {
            if(strategy == null)
                throw new NullPointerException("strategy");
            else
                return new Impl(strategy, this);
        }

        public Builder concurrencyLevel(int i)
        {
            if(concurrencyLevel != -1)
                throw new IllegalStateException((new StringBuilder("concurrency level was already set to ")).append(concurrencyLevel).toString());
            if(i <= 0)
            {
                throw new IllegalArgumentException();
            } else
            {
                concurrencyLevel = i;
                return this;
            }
        }

        int getConcurrencyLevel()
        {
            int i;
            if(concurrencyLevel == -1)
                i = 16;
            else
                i = concurrencyLevel;
            return i;
        }

        int getInitialCapacity()
        {
            int i;
            if(initialCapacity == -1)
                i = 16;
            else
                i = initialCapacity;
            return i;
        }

        public Builder initialCapacity(int i)
        {
            if(initialCapacity != -1)
                throw new IllegalStateException((new StringBuilder("initial capacity was already set to ")).append(initialCapacity).toString());
            if(i < 0)
            {
                throw new IllegalArgumentException();
            } else
            {
                initialCapacity = i;
                return this;
            }
        }

        private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
        private static final int DEFAULT_INITIAL_CAPACITY = 16;
        private static final int UNSET_CONCURRENCY_LEVEL = -1;
        private static final int UNSET_INITIAL_CAPACITY = -1;
        int concurrencyLevel;
        int initialCapacity;

        Builder()
        {
            initialCapacity = -1;
            concurrencyLevel = -1;
        }
    }

    static class ComputingImpl extends Impl
    {

        public Object get(Object obj)
        {
            int i;
            Impl.Segment segment;
            if(obj == null)
                throw new NullPointerException("key");
            i = hash(obj);
            segment = segmentFor(i);
_L7:
            Object obj1 = segment.getEntry(obj, i);
            if(obj1 != null) goto _L2; else goto _L1
_L1:
            boolean flag1;
            flag1 = false;
            segment.lock();
            obj1 = segment.getEntry(obj, i);
            if(obj1 == null)
            {
                flag1 = true;
                int j = segment.count;
                int k = j + 1;
                if(j > segment.threshold)
                    segment.expand();
                AtomicReferenceArray atomicreferencearray = segment.table;
                int l = i & atomicreferencearray.length() - 1;
                Object obj3 = atomicreferencearray.get(l);
                segment.modCount = 1 + segment.modCount;
                obj1 = computingStrategy.newEntry(obj, i, obj3);
                atomicreferencearray.set(l, obj1);
                segment.count = k;
            }
            segment.unlock();
            if(!flag1) goto _L2; else goto _L3
_L3:
            Object obj2;
            ComputingStrategy computingstrategy = computingStrategy;
            Function function = computer;
            obj2 = computingstrategy.compute(obj, obj1, function);
            if(obj2 == null)
                throw new NullPointerException("compute() returned null unexpectedly");
            break MISSING_BLOCK_LABEL_229;
            Exception exception2;
            exception2;
            if(true)
                segment.removeEntry(obj1, i);
            throw exception2;
            Exception exception1;
            exception1;
            segment.unlock();
            throw exception1;
            if(false)
                segment.removeEntry(obj1, i);
_L4:
            return obj2;
_L2:
            boolean flag = false;
_L5:
            obj2 = computingStrategy.waitForValue(obj1);
            if(obj2 != null)
                break MISSING_BLOCK_LABEL_287;
            segment.removeEntry(obj1, i);
            if(flag)
                Thread.currentThread().interrupt();
            continue; /* Loop/switch isn't completed */
            if(flag)
                Thread.currentThread().interrupt();
              goto _L4
            InterruptedException interruptedexception;
            interruptedexception;
            flag = true;
              goto _L5
            Exception exception;
            exception;
            if(flag)
                Thread.currentThread().interrupt();
            throw exception;
            if(true) goto _L7; else goto _L6
_L6:
        }

        static final long serialVersionUID;
        final Function computer;
        final ComputingStrategy computingStrategy;

        ComputingImpl(ComputingStrategy computingstrategy, Builder builder, Function function)
        {
            super(computingstrategy, builder);
            computingStrategy = computingstrategy;
            computer = function;
        }
    }

    public static interface ComputingStrategy
        extends Strategy
    {

        public abstract Object compute(Object obj, Object obj1, Function function);

        public abstract Object waitForValue(Object obj)
            throws InterruptedException;
    }

    static class Impl extends AbstractMap
        implements ConcurrentMap, Serializable
    {
        final class EntryIterator extends HashIterator
            implements Iterator
        {

            public volatile Object next()
            {
                return next();
            }

            public java.util.Map.Entry next()
            {
                return nextEntry();
            }

            final Impl this$1;

            EntryIterator()
            {
                this$1 = Impl.this;
                super();
            }
        }

        final class EntrySet extends AbstractSet
        {

            public void clear()
            {
                Impl.this.clear();
            }

            public boolean contains(Object obj)
            {
                boolean flag;
                if(!(obj instanceof java.util.Map.Entry))
                {
                    flag = false;
                } else
                {
                    java.util.Map.Entry entry = (java.util.Map.Entry)obj;
                    Object obj1 = entry.getKey();
                    if(obj1 == null)
                    {
                        flag = false;
                    } else
                    {
                        Object obj2 = get(obj1);
                        if(obj2 != null && strategy.equalValues(obj2, entry.getValue()))
                            flag = true;
                        else
                            flag = false;
                    }
                }
                return flag;
            }

            public boolean isEmpty()
            {
                return Impl.this.isEmpty();
            }

            public Iterator iterator()
            {
                return new EntryIterator();
            }

            public boolean remove(Object obj)
            {
                boolean flag;
                if(!(obj instanceof java.util.Map.Entry))
                {
                    flag = false;
                } else
                {
                    java.util.Map.Entry entry = (java.util.Map.Entry)obj;
                    Object obj1 = entry.getKey();
                    if(obj1 != null && Impl.this.remove(obj1, entry.getValue()))
                        flag = true;
                    else
                        flag = false;
                }
                return flag;
            }

            public int size()
            {
                return Impl.this.size();
            }

            final Impl this$1;

            EntrySet()
            {
                this$1 = Impl.this;
                super();
            }
        }

        static class Fields
        {

            static Field findField(String s)
            {
                Field field;
                try
                {
                    field = com/google/common/collect/CustomConcurrentHashMap$Impl.getDeclaredField(s);
                    field.setAccessible(true);
                }
                catch(NoSuchFieldException nosuchfieldexception)
                {
                    throw new AssertionError(nosuchfieldexception);
                }
                return field;
            }

            static final Field segmentMask = findField("segmentMask");
            static final Field segmentShift = findField("segmentShift");
            static final Field segments = findField("segments");
            static final Field strategy = findField("strategy");


            Fields()
            {
            }
        }

        abstract class HashIterator
        {

            final void advance()
            {
                nextExternal = null;
                break MISSING_BLOCK_LABEL_5;
                if(!nextInChain() && !nextInTable())
label0:
                    do
                    {
                        Segment segment;
                        do
                        {
                            if(nextSegmentIndex < 0)
                                break label0;
                            Segment asegment[] = segments;
                            int i = nextSegmentIndex;
                            nextSegmentIndex = i - 1;
                            segment = asegment[i];
                        } while(segment.count == 0);
                        currentTable = segment.table;
                        nextTableIndex = currentTable.length() - 1;
                    } while(!nextInTable());
                return;
            }

            boolean advanceTo(Object obj)
            {
                Strategy strategy1 = strategy;
                Object obj1 = strategy1.getKey(obj);
                Object obj2 = strategy1.getValue(obj);
                boolean flag;
                if(obj1 != null && obj2 != null)
                {
                    nextExternal = new WriteThroughEntry(obj1, obj2);
                    flag = true;
                } else
                {
                    flag = false;
                }
                return flag;
            }

            public boolean hasMoreElements()
            {
                return hasNext();
            }

            public boolean hasNext()
            {
                boolean flag;
                if(nextExternal != null)
                    flag = true;
                else
                    flag = false;
                return flag;
            }

            WriteThroughEntry nextEntry()
            {
                if(nextExternal == null)
                {
                    throw new NoSuchElementException();
                } else
                {
                    lastReturned = nextExternal;
                    advance();
                    return lastReturned;
                }
            }

            boolean nextInChain()
            {
                Strategy strategy1 = strategy;
                if(nextEntry == null) goto _L2; else goto _L1
_L1:
                nextEntry = strategy1.getNext(nextEntry);
_L7:
                if(nextEntry != null) goto _L3; else goto _L2
_L2:
                boolean flag = false;
_L5:
                return flag;
_L3:
                if(!advanceTo(nextEntry))
                    break; /* Loop/switch isn't completed */
                flag = true;
                if(true) goto _L5; else goto _L4
_L4:
                nextEntry = strategy1.getNext(nextEntry);
                if(true) goto _L7; else goto _L6
_L6:
            }

            boolean nextInTable()
            {
_L2:
                boolean flag;
                if(nextTableIndex < 0)
                {
                    flag = false;
                } else
                {
                    AtomicReferenceArray atomicreferencearray = currentTable;
                    int i = nextTableIndex;
                    nextTableIndex = i - 1;
                    Object obj = atomicreferencearray.get(i);
                    nextEntry = obj;
                    if(obj == null || !advanceTo(nextEntry) && !nextInChain())
                        continue; /* Loop/switch isn't completed */
                    flag = true;
                }
                return flag;
                if(true) goto _L2; else goto _L1
_L1:
            }

            public void remove()
            {
                if(lastReturned == null)
                {
                    throw new IllegalStateException();
                } else
                {
                    Impl.this.remove(lastReturned.getKey());
                    lastReturned = null;
                    return;
                }
            }

            AtomicReferenceArray currentTable;
            WriteThroughEntry lastReturned;
            Object nextEntry;
            WriteThroughEntry nextExternal;
            int nextSegmentIndex;
            int nextTableIndex;
            final Impl this$1;

            HashIterator()
            {
                this$1 = Impl.this;
                super();
                nextSegmentIndex = segments.length - 1;
                nextTableIndex = -1;
                advance();
            }
        }

        class InternalsImpl
            implements Internals, Serializable
        {

            public Object getEntry(Object obj)
            {
                if(obj == null)
                {
                    throw new NullPointerException("key");
                } else
                {
                    int i = hash(obj);
                    return segmentFor(i).getEntry(obj, i);
                }
            }

            public boolean removeEntry(Object obj)
            {
                if(obj == null)
                {
                    throw new NullPointerException("entry");
                } else
                {
                    int i = strategy.getHash(obj);
                    return segmentFor(i).removeEntry(obj, i);
                }
            }

            public boolean removeEntry(Object obj, Object obj1)
            {
                if(obj == null)
                {
                    throw new NullPointerException("entry");
                } else
                {
                    int i = strategy.getHash(obj);
                    return segmentFor(i).removeEntry(obj, i, obj1);
                }
            }

            static final long serialVersionUID;
            final Impl this$1;

            InternalsImpl()
            {
                this$1 = Impl.this;
                super();
            }
        }

        final class KeyIterator extends HashIterator
            implements Iterator
        {

            public Object next()
            {
                return super.nextEntry().getKey();
            }

            final Impl this$1;

            KeyIterator()
            {
                this$1 = Impl.this;
                super();
            }
        }

        final class KeySet extends AbstractSet
        {

            public void clear()
            {
                Impl.this.clear();
            }

            public boolean contains(Object obj)
            {
                return containsKey(obj);
            }

            public boolean isEmpty()
            {
                return Impl.this.isEmpty();
            }

            public Iterator iterator()
            {
                return new KeyIterator();
            }

            public boolean remove(Object obj)
            {
                boolean flag;
                if(Impl.this.remove(obj) != null)
                    flag = true;
                else
                    flag = false;
                return flag;
            }

            public int size()
            {
                return Impl.this.size();
            }

            final Impl this$1;

            KeySet()
            {
                this$1 = Impl.this;
                super();
            }
        }

        final class Segment extends ReentrantLock
        {

            void clear()
            {
                if(count == 0) goto _L2; else goto _L1
_L1:
                lock();
                AtomicReferenceArray atomicreferencearray;
                int i;
                atomicreferencearray = table;
                i = 0;
_L5:
                if(i < atomicreferencearray.length()) goto _L4; else goto _L3
_L3:
                modCount = 1 + modCount;
                count = 0;
                unlock();
_L2:
                return;
_L4:
                atomicreferencearray.set(i, null);
                i++;
                  goto _L5
                Exception exception;
                exception;
                unlock();
                throw exception;
            }

            boolean containsKey(Object obj, int i)
            {
                Strategy strategy1 = strategy;
                if(count == 0) goto _L2; else goto _L1
_L1:
                Object obj1 = getFirst(i);
_L6:
                if(obj1 != null) goto _L3; else goto _L2
_L2:
                boolean flag = false;
_L8:
                return flag;
_L3:
                if(strategy1.getHash(obj1) == i) goto _L5; else goto _L4
_L4:
                Object obj2;
                obj1 = strategy1.getNext(obj1);
                  goto _L6
_L5:
                if((obj2 = strategy1.getKey(obj1)) == null || !strategy1.equalKeys(obj2, obj)) goto _L4; else goto _L7
_L7:
                if(strategy1.getValue(obj1) != null)
                    flag = true;
                else
                    flag = false;
                  goto _L8
            }

            boolean containsValue(Object obj)
            {
                Strategy strategy1 = strategy;
                if(count == 0) goto _L2; else goto _L1
_L1:
                AtomicReferenceArray atomicreferencearray;
                int i;
                int j;
                atomicreferencearray = table;
                i = atomicreferencearray.length();
                j = 0;
_L4:
                if(j < i) goto _L3; else goto _L2
_L2:
                boolean flag = false;
_L9:
                return flag;
_L3:
                Object obj1 = atomicreferencearray.get(j);
_L6:
label0:
                {
                    if(obj1 != null)
                        break label0;
                    j++;
                }
                  goto _L4
                Object obj2 = strategy1.getValue(obj1);
                  goto _L5
_L8:
                obj1 = strategy1.getNext(obj1);
                  goto _L6
_L5:
                if(obj2 == null || !strategy1.equalValues(obj2, obj)) goto _L8; else goto _L7
_L7:
                flag = true;
                  goto _L9
            }

            void expand()
            {
                AtomicReferenceArray atomicreferencearray;
                int i;
                atomicreferencearray = table;
                i = atomicreferencearray.length();
                if(i < 0x40000000) goto _L2; else goto _L1
_L1:
                return;
_L2:
                Strategy strategy1;
                AtomicReferenceArray atomicreferencearray1;
                int j;
                int k;
                strategy1 = strategy;
                atomicreferencearray1 = newEntryArray(i << 1);
                threshold = (3 * atomicreferencearray1.length()) / 4;
                j = atomicreferencearray1.length() - 1;
                k = 0;
_L8:
label0:
                {
                    if(k < i)
                        break label0;
                    table = atomicreferencearray1;
                }
                if(true) goto _L1; else goto _L3
_L3:
                Object obj = atomicreferencearray.get(k);
                if(obj == null) goto _L5; else goto _L4
_L4:
                Object obj1;
                int l;
                obj1 = strategy1.getNext(obj);
                l = j & strategy1.getHash(obj);
                if(obj1 != null) goto _L7; else goto _L6
_L6:
                atomicreferencearray1.set(l, obj);
_L5:
                k++;
                  goto _L8
_L7:
                Object obj2;
                int i1;
                Object obj3;
                obj2 = obj;
                i1 = l;
                obj3 = obj1;
_L9:
label1:
                {
                    if(obj3 != null)
                        break label1;
                    atomicreferencearray1.set(i1, obj2);
                    Object obj4 = obj;
                    while(obj4 != obj2) 
                    {
                        Object obj5 = strategy1.getKey(obj4);
                        if(obj5 != null)
                        {
                            int k1 = j & strategy1.getHash(obj4);
                            Object obj6 = atomicreferencearray1.get(k1);
                            atomicreferencearray1.set(k1, strategy1.copyEntry(obj5, obj4, obj6));
                        }
                        obj4 = strategy1.getNext(obj4);
                    }
                }
                  goto _L5
                int j1 = j & strategy1.getHash(obj3);
                if(j1 != i1)
                {
                    i1 = j1;
                    obj2 = obj3;
                }
                obj3 = strategy1.getNext(obj3);
                  goto _L9
            }

            Object get(Object obj, int i)
            {
                Object obj1 = getEntry(obj, i);
                Object obj2;
                if(obj1 == null)
                    obj2 = null;
                else
                    obj2 = strategy.getValue(obj1);
                return obj2;
            }

            public Object getEntry(Object obj, int i)
            {
                Strategy strategy1 = strategy;
                if(count == 0) goto _L2; else goto _L1
_L1:
                Object obj2 = getFirst(i);
_L6:
                if(obj2 != null) goto _L3; else goto _L2
_L2:
                Object obj1 = null;
_L8:
                return obj1;
_L3:
                if(strategy1.getHash(obj2) == i) goto _L5; else goto _L4
_L4:
                Object obj3;
                obj2 = strategy1.getNext(obj2);
                  goto _L6
_L5:
                if((obj3 = strategy1.getKey(obj2)) == null || !strategy1.equalKeys(obj3, obj)) goto _L4; else goto _L7
_L7:
                obj1 = obj2;
                  goto _L8
            }

            Object getFirst(int i)
            {
                AtomicReferenceArray atomicreferencearray = table;
                return atomicreferencearray.get(i & atomicreferencearray.length() - 1);
            }

            AtomicReferenceArray newEntryArray(int i)
            {
                return new AtomicReferenceArray(i);
            }

            Object put(Object obj, int i, Object obj1, boolean flag)
            {
                Strategy strategy1;
                strategy1 = strategy;
                lock();
                int k;
                AtomicReferenceArray atomicreferencearray;
                int l;
                Object obj2;
                Object obj3;
                int j = count;
                k = j + 1;
                if(j > threshold)
                    expand();
                atomicreferencearray = table;
                l = i & atomicreferencearray.length() - 1;
                obj2 = atomicreferencearray.get(l);
                obj3 = obj2;
_L5:
                if(obj3 != null) goto _L2; else goto _L1
_L1:
                modCount = 1 + modCount;
                Object obj8 = strategy1.newEntry(obj, i, obj2);
                strategy1.setValue(obj8, obj1);
                atomicreferencearray.set(l, obj8);
                count = k;
                Object obj7;
                unlock();
                obj7 = null;
_L4:
                return obj7;
_L2:
                Object obj6;
                Object obj4 = strategy1.getKey(obj3);
                if(strategy1.getHash(obj3) != i || obj4 == null || !strategy1.equalKeys(obj, obj4))
                    break; /* Loop/switch isn't completed */
                obj6 = strategy1.getValue(obj3);
                if(flag && obj6 != null)
                {
                    unlock();
                    obj7 = obj6;
                    continue; /* Loop/switch isn't completed */
                }
                strategy1.setValue(obj3, obj1);
                unlock();
                obj7 = obj6;
                if(true) goto _L4; else goto _L3
_L3:
                Object obj5 = strategy1.getNext(obj3);
                obj3 = obj5;
                  goto _L5
                Exception exception;
                exception;
                unlock();
                throw exception;
            }

            Object remove(Object obj, int i)
            {
                Strategy strategy1;
                strategy1 = strategy;
                lock();
                int j;
                AtomicReferenceArray atomicreferencearray;
                int k;
                Object obj1;
                j = count - 1;
                atomicreferencearray = table;
                k = i & atomicreferencearray.length() - 1;
                obj1 = atomicreferencearray.get(k);
                Object obj2 = obj1;
_L6:
                if(obj2 != null) goto _L2; else goto _L1
_L1:
                Object obj9;
                unlock();
                obj9 = null;
_L3:
                return obj9;
_L2:
                Object obj5;
                Object obj6;
                Object obj7;
                Object obj3 = strategy1.getKey(obj2);
                if(strategy1.getHash(obj2) != i || obj3 == null || !strategy1.equalKeys(obj3, obj))
                    break MISSING_BLOCK_LABEL_219;
                obj5 = strategy.getValue(obj2);
                modCount = 1 + modCount;
                obj6 = strategy1.getNext(obj2);
                obj7 = obj1;
_L4:
                if(obj7 != obj2)
                    break MISSING_BLOCK_LABEL_177;
                atomicreferencearray.set(k, obj6);
                count = j;
                unlock();
                obj9 = obj5;
                  goto _L3
                Object obj8 = strategy1.getKey(obj7);
                if(obj8 != null)
                    obj6 = strategy1.copyEntry(obj8, obj7, obj6);
                obj7 = strategy1.getNext(obj7);
                  goto _L4
                Object obj4 = strategy1.getNext(obj2);
                obj2 = obj4;
                if(true) goto _L6; else goto _L5
_L5:
                Exception exception;
                exception;
                unlock();
                throw exception;
            }

            boolean remove(Object obj, int i, Object obj1)
            {
                Strategy strategy1;
                strategy1 = strategy;
                lock();
                int j;
                AtomicReferenceArray atomicreferencearray;
                int k;
                Object obj2;
                j = count - 1;
                atomicreferencearray = table;
                k = i & atomicreferencearray.length() - 1;
                obj2 = atomicreferencearray.get(k);
                Object obj3 = obj2;
_L9:
                if(obj3 != null) goto _L2; else goto _L1
_L1:
                boolean flag;
                unlock();
                flag = false;
_L7:
                return flag;
_L2:
                Object obj7;
                Object obj8;
                Object obj4 = strategy1.getKey(obj3);
                if(strategy1.getHash(obj3) != i || obj4 == null || !strategy1.equalKeys(obj4, obj))
                    break; /* Loop/switch isn't completed */
                Object obj6 = strategy.getValue(obj3);
                if(obj1 != obj6 && (obj1 == null || obj6 == null || !strategy1.equalValues(obj6, obj1)))
                    break MISSING_BLOCK_LABEL_258;
                modCount = 1 + modCount;
                obj7 = strategy1.getNext(obj3);
                obj8 = obj2;
_L5:
                if(obj8 != obj3) goto _L4; else goto _L3
_L3:
                atomicreferencearray.set(k, obj7);
                count = j;
                unlock();
                flag = true;
                continue; /* Loop/switch isn't completed */
_L4:
                Object obj10;
                Object obj9 = strategy1.getKey(obj8);
                if(obj9 != null)
                    obj7 = strategy1.copyEntry(obj9, obj8, obj7);
                obj10 = strategy1.getNext(obj8);
                obj8 = obj10;
                  goto _L5
                unlock();
                flag = false;
                if(true) goto _L7; else goto _L6
_L6:
                Object obj5 = strategy1.getNext(obj3);
                obj3 = obj5;
                if(true) goto _L9; else goto _L8
_L8:
                Exception exception;
                exception;
                unlock();
                throw exception;
            }

            public boolean removeEntry(Object obj, int i)
            {
                Strategy strategy1;
                strategy1 = strategy;
                lock();
                int j;
                AtomicReferenceArray atomicreferencearray;
                int k;
                Object obj1;
                j = count - 1;
                atomicreferencearray = table;
                k = i & atomicreferencearray.length() - 1;
                obj1 = atomicreferencearray.get(k);
                Object obj2 = obj1;
_L6:
                if(obj2 != null) goto _L2; else goto _L1
_L1:
                boolean flag;
                unlock();
                flag = false;
_L3:
                return flag;
_L2:
                Object obj4;
                Object obj5;
                if(strategy1.getHash(obj2) != i || !obj.equals(obj2))
                    break MISSING_BLOCK_LABEL_184;
                modCount = 1 + modCount;
                obj4 = strategy1.getNext(obj2);
                obj5 = obj1;
_L4:
                if(obj5 != obj2)
                    break MISSING_BLOCK_LABEL_142;
                atomicreferencearray.set(k, obj4);
                count = j;
                unlock();
                flag = true;
                  goto _L3
                Object obj6 = strategy1.getKey(obj5);
                if(obj6 != null)
                    obj4 = strategy1.copyEntry(obj6, obj5, obj4);
                obj5 = strategy1.getNext(obj5);
                  goto _L4
                Object obj3 = strategy1.getNext(obj2);
                obj2 = obj3;
                if(true) goto _L6; else goto _L5
_L5:
                Exception exception;
                exception;
                unlock();
                throw exception;
            }

            public boolean removeEntry(Object obj, int i, Object obj1)
            {
                Strategy strategy1;
                strategy1 = strategy;
                lock();
                int j;
                AtomicReferenceArray atomicreferencearray;
                int k;
                Object obj2;
                j = count - 1;
                atomicreferencearray = table;
                k = i & atomicreferencearray.length() - 1;
                obj2 = atomicreferencearray.get(k);
                Object obj3 = obj2;
_L9:
                if(obj3 != null) goto _L2; else goto _L1
_L1:
                boolean flag;
                unlock();
                flag = false;
_L7:
                return flag;
_L2:
                Object obj6;
                Object obj7;
                if(strategy1.getHash(obj3) != i || !obj.equals(obj3))
                    break; /* Loop/switch isn't completed */
                Object obj5 = strategy1.getValue(obj3);
                if(obj5 != obj1 && (obj1 == null || !strategy1.equalValues(obj5, obj1)))
                    break MISSING_BLOCK_LABEL_228;
                modCount = 1 + modCount;
                obj6 = strategy1.getNext(obj3);
                obj7 = obj2;
_L5:
                if(obj7 != obj3) goto _L4; else goto _L3
_L3:
                atomicreferencearray.set(k, obj6);
                count = j;
                unlock();
                flag = true;
                continue; /* Loop/switch isn't completed */
_L4:
                Object obj9;
                Object obj8 = strategy1.getKey(obj7);
                if(obj8 != null)
                    obj6 = strategy1.copyEntry(obj8, obj7, obj6);
                obj9 = strategy1.getNext(obj7);
                obj7 = obj9;
                  goto _L5
                unlock();
                flag = false;
                if(true) goto _L7; else goto _L6
_L6:
                Object obj4 = strategy1.getNext(obj3);
                obj3 = obj4;
                if(true) goto _L9; else goto _L8
_L8:
                Exception exception;
                exception;
                unlock();
                throw exception;
            }

            Object replace(Object obj, int i, Object obj1)
            {
                Strategy strategy1;
                strategy1 = strategy;
                lock();
                Object obj2 = getFirst(i);
                Object obj3 = obj2;
_L6:
                if(obj3 != null) goto _L2; else goto _L1
_L1:
                Object obj7;
                unlock();
                obj7 = null;
_L4:
                return obj7;
_L2:
                Object obj6;
                Object obj4 = strategy1.getKey(obj3);
                if(strategy1.getHash(obj3) != i || obj4 == null || !strategy1.equalKeys(obj, obj4))
                    break; /* Loop/switch isn't completed */
                obj6 = strategy1.getValue(obj3);
                if(obj6 == null)
                {
                    unlock();
                    obj7 = null;
                    continue; /* Loop/switch isn't completed */
                }
                strategy1.setValue(obj3, obj1);
                unlock();
                obj7 = obj6;
                if(true) goto _L4; else goto _L3
_L3:
                Object obj5 = strategy1.getNext(obj3);
                obj3 = obj5;
                if(true) goto _L6; else goto _L5
_L5:
                Exception exception;
                exception;
                unlock();
                throw exception;
            }

            boolean replace(Object obj, int i, Object obj1, Object obj2)
            {
                Strategy strategy1;
                strategy1 = strategy;
                lock();
                Object obj3 = getFirst(i);
                Object obj4 = obj3;
_L6:
                if(obj4 != null) goto _L2; else goto _L1
_L1:
                boolean flag;
                unlock();
                flag = false;
_L4:
                return flag;
_L2:
                Object obj7;
                Object obj5 = strategy1.getKey(obj4);
                if(strategy1.getHash(obj4) != i || obj5 == null || !strategy1.equalKeys(obj, obj5))
                    break; /* Loop/switch isn't completed */
                obj7 = strategy1.getValue(obj4);
                if(obj7 == null)
                {
                    unlock();
                    flag = false;
                    continue; /* Loop/switch isn't completed */
                }
                if(!strategy1.equalValues(obj7, obj1))
                    break; /* Loop/switch isn't completed */
                strategy1.setValue(obj4, obj2);
                unlock();
                flag = true;
                if(true) goto _L4; else goto _L3
_L3:
                Object obj6 = strategy1.getNext(obj4);
                obj4 = obj6;
                if(true) goto _L6; else goto _L5
_L5:
                Exception exception;
                exception;
                unlock();
                throw exception;
            }

            void setTable(AtomicReferenceArray atomicreferencearray)
            {
                threshold = (3 * atomicreferencearray.length()) / 4;
                table = atomicreferencearray;
            }

            volatile int count;
            int modCount;
            volatile AtomicReferenceArray table;
            final Impl this$1;
            int threshold;

            Segment(int i)
            {
                this$1 = Impl.this;
                super();
                setTable(newEntryArray(i));
            }
        }

        final class ValueIterator extends HashIterator
            implements Iterator
        {

            public Object next()
            {
                return super.nextEntry().getValue();
            }

            final Impl this$1;

            ValueIterator()
            {
                this$1 = Impl.this;
                super();
            }
        }

        final class Values extends AbstractCollection
        {

            public void clear()
            {
                Impl.this.clear();
            }

            public boolean contains(Object obj)
            {
                return containsValue(obj);
            }

            public boolean isEmpty()
            {
                return Impl.this.isEmpty();
            }

            public Iterator iterator()
            {
                return new ValueIterator();
            }

            public int size()
            {
                return Impl.this.size();
            }

            final Impl this$1;

            Values()
            {
                this$1 = Impl.this;
                super();
            }
        }

        final class WriteThroughEntry extends AbstractMapEntry
        {

            public Object getKey()
            {
                return key;
            }

            public Object getValue()
            {
                return value;
            }

            public Object setValue(Object obj)
            {
                if(obj == null)
                {
                    throw new NullPointerException();
                } else
                {
                    Object obj1 = put(getKey(), obj);
                    value = obj;
                    return obj1;
                }
            }

            final Object key;
            final Impl this$1;
            Object value;

            WriteThroughEntry(Object obj, Object obj1)
            {
                this$1 = Impl.this;
                super();
                key = obj;
                value = obj1;
            }
        }


        private void readObject(ObjectInputStream objectinputstream)
            throws IOException, ClassNotFoundException
        {
            int i;
            int j;
            Strategy strategy1;
            i = objectinputstream.readInt();
            j = objectinputstream.readInt();
            strategy1 = (Strategy)objectinputstream.readObject();
            if(j > 0x10000)
                j = 0x10000;
              goto _L1
_L2:
            int k;
            int l;
            int i1;
            if(l < j)
                break MISSING_BLOCK_LABEL_216;
            Fields.segmentShift.set(this, Integer.valueOf(32 - k));
            Fields.segmentMask.set(this, Integer.valueOf(l - 1));
            Fields.segments.set(this, newSegmentArray(l));
            if(i > 0x40000000)
                i = 0x40000000;
            i1 = i / l;
            if(i1 * l < i)
                i1++;
            for(j1 = 1; j1 < i1; j1 <<= 1)
                break MISSING_BLOCK_LABEL_228;

            k1 = 0;
            while(k1 < segments.length) 
            {
                segments[k1] = new Segment(j1);
                k1++;
            }
            Fields.strategy.set(this, strategy1);
            do
            {
                Object obj = objectinputstream.readObject();
                if(obj == null)
                    return;
                put(obj, objectinputstream.readObject());
            } while(true);
            illegalaccessexception;
            throw new AssertionError(illegalaccessexception);
_L1:
            k = 0;
            l = 1;
              goto _L2
            k++;
            l <<= 1;
              goto _L2
        }

        private void writeObject(ObjectOutputStream objectoutputstream)
            throws IOException
        {
            objectoutputstream.writeInt(size());
            objectoutputstream.writeInt(segments.length);
            objectoutputstream.writeObject(strategy);
            Iterator iterator = entrySet().iterator();
            do
            {
                if(!iterator.hasNext())
                {
                    objectoutputstream.writeObject(null);
                    return;
                }
                java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
                objectoutputstream.writeObject(entry.getKey());
                objectoutputstream.writeObject(entry.getValue());
            } while(true);
        }

        public void clear()
        {
            Segment asegment[] = segments;
            int i = asegment.length;
            int j = 0;
            do
            {
                if(j >= i)
                    return;
                asegment[j].clear();
                j++;
            } while(true);
        }

        public boolean containsKey(Object obj)
        {
            if(obj == null)
            {
                throw new NullPointerException("key");
            } else
            {
                int i = hash(obj);
                return segmentFor(i).containsKey(obj, i);
            }
        }

        public boolean containsValue(Object obj)
        {
            Segment asegment[];
            int ai[];
            int i;
            if(obj == null)
                throw new NullPointerException("value");
            asegment = segments;
            ai = new int[asegment.length];
            i = 0;
_L14:
            if(i < 2) goto _L2; else goto _L1
_L1:
            int j1;
            int k1;
            j1 = asegment.length;
            k1 = 0;
_L15:
            if(k1 < j1) goto _L4; else goto _L3
_L3:
            boolean flag2 = false;
            int j2 = asegment.length;
            int k2 = 0;
_L16:
            if(k2 < j2) goto _L6; else goto _L5
_L5:
            int l2;
            int i3;
            l2 = asegment.length;
            i3 = 0;
_L17:
            boolean flag;
            if(i3 < l2)
                break MISSING_BLOCK_LABEL_292;
            flag = flag2;
_L11:
            return flag;
_L2:
            int j;
            int k;
            j = 0;
            k = 0;
_L12:
            if(k < asegment.length) goto _L8; else goto _L7
_L7:
            boolean flag1 = true;
            if(j == 0) goto _L10; else goto _L9
_L9:
            int i1 = 0;
_L13:
            if(i1 < asegment.length)
            {
label0:
                {
                    asegment[i1].count;
                    if(ai[i1] == asegment[i1].modCount)
                        break label0;
                    flag1 = false;
                }
            }
_L10:
            if(!flag1)
                break MISSING_BLOCK_LABEL_214;
            flag = false;
              goto _L11
_L8:
label1:
            {
                asegment[k].count;
                int l = asegment[k].modCount;
                ai[k] = l;
                j += l;
                if(!asegment[k].containsValue(obj))
                    break label1;
                flag = true;
            }
              goto _L11
            k++;
              goto _L12
            i1++;
              goto _L13
            i++;
              goto _L14
_L4:
            asegment[k1].lock();
            k1++;
              goto _L15
_L6:
            boolean flag3 = asegment[k2].containsValue(obj);
label2:
            {
                if(!flag3)
                    break label2;
                flag2 = true;
            }
              goto _L5
            k2++;
              goto _L16
            Exception exception;
            exception;
            int l1 = asegment.length;
            int i2 = 0;
            do
            {
                if(i2 >= l1)
                    throw exception;
                asegment[i2].unlock();
                i2++;
            } while(true);
            asegment[i3].unlock();
            i3++;
              goto _L17
        }

        public Set entrySet()
        {
            Set set = entrySet;
            Object obj;
            if(set != null)
            {
                obj = set;
            } else
            {
                obj = new EntrySet();
                entrySet = ((Set) (obj));
            }
            return ((Set) (obj));
        }

        public Object get(Object obj)
        {
            if(obj == null)
            {
                throw new NullPointerException("key");
            } else
            {
                int i = hash(obj);
                return segmentFor(i).get(obj, i);
            }
        }

        int hash(Object obj)
        {
            return CustomConcurrentHashMap.rehash(strategy.hashKey(obj));
        }

        public boolean isEmpty()
        {
            Segment asegment[];
            int ai[];
            int i;
            int j;
            asegment = segments;
            ai = new int[asegment.length];
            i = 0;
            j = 0;
_L7:
            if(j < asegment.length) goto _L2; else goto _L1
_L1:
            if(i == 0) goto _L4; else goto _L3
_L3:
            int l = 0;
_L8:
            if(l < asegment.length) goto _L5; else goto _L4
_L4:
            boolean flag = true;
_L6:
            return flag;
_L2:
label0:
            {
                if(asegment[j].count == 0)
                    break label0;
                flag = false;
            }
              goto _L6
            int k = asegment[j].modCount;
            ai[j] = k;
            i += k;
            j++;
              goto _L7
_L5:
label1:
            {
                if(asegment[l].count == 0 && ai[l] == asegment[l].modCount)
                    break label1;
                flag = false;
            }
              goto _L6
            l++;
              goto _L8
        }

        public Set keySet()
        {
            Set set = keySet;
            Object obj;
            if(set != null)
            {
                obj = set;
            } else
            {
                obj = new KeySet();
                keySet = ((Set) (obj));
            }
            return ((Set) (obj));
        }

        Segment[] newSegmentArray(int i)
        {
            return (Segment[])Array.newInstance(com/google/common/collect/CustomConcurrentHashMap$Impl$Segment, i);
        }

        public Object put(Object obj, Object obj1)
        {
            if(obj == null)
                throw new NullPointerException("key");
            if(obj1 == null)
            {
                throw new NullPointerException("value");
            } else
            {
                int i = hash(obj);
                return segmentFor(i).put(obj, i, obj1, false);
            }
        }

        public void putAll(Map map)
        {
            Iterator iterator = map.entrySet().iterator();
            do
            {
                if(!iterator.hasNext())
                    return;
                java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
                put(entry.getKey(), entry.getValue());
            } while(true);
        }

        public Object putIfAbsent(Object obj, Object obj1)
        {
            if(obj == null)
                throw new NullPointerException("key");
            if(obj1 == null)
            {
                throw new NullPointerException("value");
            } else
            {
                int i = hash(obj);
                return segmentFor(i).put(obj, i, obj1, true);
            }
        }

        public Object remove(Object obj)
        {
            if(obj == null)
            {
                throw new NullPointerException("key");
            } else
            {
                int i = hash(obj);
                return segmentFor(i).remove(obj, i);
            }
        }

        public boolean remove(Object obj, Object obj1)
        {
            if(obj == null)
            {
                throw new NullPointerException("key");
            } else
            {
                int i = hash(obj);
                return segmentFor(i).remove(obj, i, obj1);
            }
        }

        public Object replace(Object obj, Object obj1)
        {
            if(obj == null)
                throw new NullPointerException("key");
            if(obj1 == null)
            {
                throw new NullPointerException("value");
            } else
            {
                int i = hash(obj);
                return segmentFor(i).replace(obj, i, obj1);
            }
        }

        public boolean replace(Object obj, Object obj1, Object obj2)
        {
            if(obj == null)
                throw new NullPointerException("key");
            if(obj1 == null)
                throw new NullPointerException("oldValue");
            if(obj2 == null)
            {
                throw new NullPointerException("newValue");
            } else
            {
                int i = hash(obj);
                return segmentFor(i).replace(obj, i, obj1, obj2);
            }
        }

        Segment segmentFor(int i)
        {
            return segments[i >>> segmentShift & segmentMask];
        }

        public int size()
        {
            Segment asegment[];
            long l;
            long l1;
            int ai[];
            int i;
            asegment = segments;
            l = 0L;
            l1 = 0L;
            ai = new int[asegment.length];
            i = 0;
_L14:
            if(i < 2) goto _L2; else goto _L1
_L1:
            if(l1 == l) goto _L4; else goto _L3
_L3:
            int k1;
            int i2;
            l = 0L;
            k1 = asegment.length;
            i2 = 0;
_L17:
            if(i2 < k1) goto _L6; else goto _L5
_L5:
            int j2;
            int k2;
            j2 = asegment.length;
            k2 = 0;
_L18:
            if(k2 < j2) goto _L8; else goto _L7
_L7:
            int l2;
            int i3;
            l2 = asegment.length;
            i3 = 0;
_L19:
            if(i3 < l2)
                break MISSING_BLOCK_LABEL_250;
_L4:
            int j;
            int k;
            int i1;
            int j1;
            int j3;
            if(l > 0x7fffffffL)
                j1 = 0x7fffffff;
            else
                j1 = (int)l;
            return j1;
_L2:
            l1 = 0L;
            l = 0L;
            j = 0;
            k = 0;
_L15:
            if(k < asegment.length) goto _L10; else goto _L9
_L9:
            if(j == 0) goto _L12; else goto _L11
_L11:
            j3 = 0;
_L16:
            if(j3 < asegment.length)
            {
label0:
                {
                    l1 += asegment[j3].count;
                    if(ai[j3] == asegment[j3].modCount)
                        break label0;
                    l1 = -1L;
                }
            }
_L12:
            if(l1 == l) goto _L1; else goto _L13
_L13:
            i++;
              goto _L14
_L10:
            l += asegment[k].count;
            i1 = asegment[k].modCount;
            ai[k] = i1;
            j += i1;
            k++;
              goto _L15
            j3++;
              goto _L16
_L6:
            asegment[i2].lock();
            i2++;
              goto _L17
_L8:
            l += asegment[k2].count;
            k2++;
              goto _L18
            asegment[i3].unlock();
            i3++;
              goto _L19
        }

        public Collection values()
        {
            Collection collection = values;
            Object obj;
            if(collection != null)
            {
                obj = collection;
            } else
            {
                obj = new Values();
                values = ((Collection) (obj));
            }
            return ((Collection) (obj));
        }

        static final int MAXIMUM_CAPACITY = 0x40000000;
        static final int MAX_SEGMENTS = 0x10000;
        static final int RETRIES_BEFORE_LOCK = 2;
        private static final long serialVersionUID = 1L;
        Set entrySet;
        Set keySet;
        final int segmentMask;
        final int segmentShift;
        final Segment segments[];
        final Strategy strategy;
        Collection values;

        Impl(Strategy strategy1, Builder builder)
        {
            int i;
            int j;
            int k;
            int l;
            i = builder.getConcurrencyLevel();
            j = builder.getInitialCapacity();
            if(i > 0x10000)
                i = 0x10000;
            k = 0;
            l = 1;
_L5:
            if(l < i) goto _L2; else goto _L1
_L1:
            int i1;
            int j1;
            segmentShift = 32 - k;
            segmentMask = l - 1;
            segments = newSegmentArray(l);
            if(j > 0x40000000)
                j = 0x40000000;
            i1 = j / l;
            if(i1 * l < j)
                i1++;
            j1 = 1;
_L6:
            if(j1 < i1) goto _L4; else goto _L3
_L3:
            int k1 = 0;
_L7:
            if(k1 >= segments.length)
            {
                strategy = strategy1;
                strategy1.setInternals(new InternalsImpl());
                return;
            }
            break MISSING_BLOCK_LABEL_158;
_L2:
            k++;
            l <<= 1;
              goto _L5
_L4:
            j1 <<= 1;
              goto _L6
            segments[k1] = new Segment(j1);
            k1++;
              goto _L7
        }
    }

    public static interface Internals
    {

        public abstract Object getEntry(Object obj);

        public abstract boolean removeEntry(Object obj);

        public abstract boolean removeEntry(Object obj, Object obj1);
    }

    static class SimpleInternalEntry
    {

        final int hash;
        final Object key;
        final SimpleInternalEntry next;
        volatile Object value;

        SimpleInternalEntry(Object obj, int i, Object obj1, SimpleInternalEntry simpleinternalentry)
        {
            key = obj;
            hash = i;
            value = obj1;
            next = simpleinternalentry;
        }
    }

    static class SimpleStrategy
        implements Strategy
    {

        public SimpleInternalEntry copyEntry(Object obj, SimpleInternalEntry simpleinternalentry, SimpleInternalEntry simpleinternalentry1)
        {
            return new SimpleInternalEntry(obj, simpleinternalentry.hash, simpleinternalentry.value, simpleinternalentry1);
        }

        public volatile Object copyEntry(Object obj, Object obj1, Object obj2)
        {
            return copyEntry((Object)obj, (SimpleInternalEntry)obj1, (SimpleInternalEntry)obj2);
        }

        public boolean equalKeys(Object obj, Object obj1)
        {
            return obj.equals(obj1);
        }

        public boolean equalValues(Object obj, Object obj1)
        {
            return obj.equals(obj1);
        }

        public int getHash(SimpleInternalEntry simpleinternalentry)
        {
            return simpleinternalentry.hash;
        }

        public volatile int getHash(Object obj)
        {
            return getHash((SimpleInternalEntry)obj);
        }

        public Object getKey(SimpleInternalEntry simpleinternalentry)
        {
            return simpleinternalentry.key;
        }

        public volatile Object getKey(Object obj)
        {
            return getKey((SimpleInternalEntry)obj);
        }

        public SimpleInternalEntry getNext(SimpleInternalEntry simpleinternalentry)
        {
            return simpleinternalentry.next;
        }

        public volatile Object getNext(Object obj)
        {
            return getNext((SimpleInternalEntry)obj);
        }

        public Object getValue(SimpleInternalEntry simpleinternalentry)
        {
            return simpleinternalentry.value;
        }

        public volatile Object getValue(Object obj)
        {
            return getValue((SimpleInternalEntry)obj);
        }

        public int hashKey(Object obj)
        {
            return obj.hashCode();
        }

        public SimpleInternalEntry newEntry(Object obj, int i, SimpleInternalEntry simpleinternalentry)
        {
            return new SimpleInternalEntry(obj, i, null, simpleinternalentry);
        }

        public volatile Object newEntry(Object obj, int i, Object obj1)
        {
            return newEntry((Object)obj, i, (SimpleInternalEntry)obj1);
        }

        public void setInternals(Internals internals)
        {
        }

        public void setValue(SimpleInternalEntry simpleinternalentry, Object obj)
        {
            simpleinternalentry.value = obj;
        }

        public volatile void setValue(Object obj, Object obj1)
        {
            setValue((SimpleInternalEntry)obj, (Object)obj1);
        }

        SimpleStrategy()
        {
        }
    }

    public static interface Strategy
    {

        public abstract Object copyEntry(Object obj, Object obj1, Object obj2);

        public abstract boolean equalKeys(Object obj, Object obj1);

        public abstract boolean equalValues(Object obj, Object obj1);

        public abstract int getHash(Object obj);

        public abstract Object getKey(Object obj);

        public abstract Object getNext(Object obj);

        public abstract Object getValue(Object obj);

        public abstract int hashKey(Object obj);

        public abstract Object newEntry(Object obj, int i, Object obj1);

        public abstract void setInternals(Internals internals);

        public abstract void setValue(Object obj, Object obj1);
    }


    private CustomConcurrentHashMap()
    {
    }

    private static int rehash(int i)
    {
        int j = i + (0xffffcd7d ^ i << 15);
        int k = j ^ j >>> 10;
        int l = k + (k << 3);
        int i1 = l ^ l >>> 6;
        int j1 = i1 + ((i1 << 2) + (i1 << 14));
        return j1 ^ j1 >>> 16;
    }

}
