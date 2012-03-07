// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.qrcode.encoder;


public final class BitVector
{

    public BitVector()
    {
        sizeInBits = 0;
        array = new byte[32];
    }

    private void appendByte(int i)
    {
        if(sizeInBits >> 3 == array.length)
        {
            byte abyte0[] = new byte[array.length << 1];
            System.arraycopy(array, 0, abyte0, 0, array.length);
            array = abyte0;
        }
        array[sizeInBits >> 3] = (byte)i;
        sizeInBits = 8 + sizeInBits;
    }

    public void appendBit(int i)
    {
        if(i != 0 && i != 1)
            throw new IllegalArgumentException("Bad bit");
        int j = 7 & sizeInBits;
        if(j == 0)
        {
            appendByte(0);
            sizeInBits = sizeInBits - 8;
        }
        byte abyte0[] = array;
        int k = sizeInBits >> 3;
        abyte0[k] = (byte)(abyte0[k] | i << 7 - j);
        sizeInBits = 1 + sizeInBits;
    }

    public void appendBitVector(BitVector bitvector)
    {
        int i = bitvector.size();
        for(int j = 0; j < i; j++)
            appendBit(bitvector.at(j));

    }

    public void appendBits(int i, int j)
    {
        if(j < 0 || j > 32)
            throw new IllegalArgumentException("Num bits must be between 0 and 32");
        for(int k = j; k > 0;)
            if((7 & sizeInBits) == 0 && k >= 8)
            {
                appendByte(0xff & i >> k - 8);
                k -= 8;
            } else
            {
                appendBit(1 & i >> k - 1);
                k--;
            }

    }

    public int at(int i)
    {
        if(i < 0 || i >= sizeInBits)
            throw new IllegalArgumentException("Bad index: " + i);
        else
            return 1 & (0xff & array[i >> 3]) >> 7 - (i & 7);
    }

    public byte[] getArray()
    {
        return array;
    }

    public int size()
    {
        return sizeInBits;
    }

    public int sizeInBytes()
    {
        return 7 + sizeInBits >> 3;
    }

    public String toString()
    {
        StringBuffer stringbuffer = new StringBuffer(sizeInBits);
        int i = 0;
        while(i < sizeInBits) 
        {
            if(at(i) == 0)
                stringbuffer.append('0');
            else
            if(at(i) == 1)
                stringbuffer.append('1');
            else
                throw new IllegalArgumentException("Byte isn't 0 or 1");
            i++;
        }
        return stringbuffer.toString();
    }

    public void xor(BitVector bitvector)
    {
        if(sizeInBits != bitvector.size())
            throw new IllegalArgumentException("BitVector sizes don't match");
        int i = 7 + sizeInBits >> 3;
        for(int j = 0; j < i; j++)
        {
            byte abyte0[] = array;
            abyte0[j] = (byte)(abyte0[j] ^ bitvector.array[j]);
        }

    }

    private static final int DEFAULT_SIZE_IN_BYTES = 32;
    private byte array[];
    private int sizeInBits;
}
