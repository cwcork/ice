// **********************************************************************
//
// Copyright (c) 2003-2005 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

package Ice;

public class _ObjectDelM implements _ObjectDel
{
    public boolean
    ice_isA(String __id, java.util.Map __context)
        throws IceInternal.NonRepeatable
    {
	IceInternal.Outgoing __og = __connection.getOutgoing(__reference, "ice_isA", OperationMode.Nonmutating,
							     __context, __compress);
        try
        {
            IceInternal.BasicStream __is = __og.is();
            IceInternal.BasicStream __os = __og.os();
            __os.writeString(__id);
            if(!__og.invoke())
            {
                throw new UnknownUserException();
            }
            try
            {
                return __is.readBool();
            }
            catch(LocalException __ex)
            {
                throw new IceInternal.NonRepeatable(__ex);
            }
        }
        finally
        {
	    __connection.reclaimOutgoing(__og);
        }
    }

    public void
    ice_ping(java.util.Map __context)
        throws IceInternal.NonRepeatable
    {
	IceInternal.Outgoing __og = __connection.getOutgoing(__reference, "ice_ping", OperationMode.Nonmutating,
							     __context, __compress);
        try
        {
            if(!__og.invoke())
            {
                throw new UnknownUserException();
            }
        }
        finally
        {
	    __connection.reclaimOutgoing(__og);
        }
    }

    public String[]
    ice_ids(java.util.Map __context)
        throws IceInternal.NonRepeatable
    {
	IceInternal.Outgoing __og = __connection.getOutgoing(__reference, "ice_ids", OperationMode.Nonmutating,
							     __context, __compress);
        try
        {
            IceInternal.BasicStream __is = __og.is();
            if(!__og.invoke())
            {
                throw new UnknownUserException();
            }
            try
            {
                return __is.readStringSeq();
            }
            catch(LocalException __ex)
            {
                throw new IceInternal.NonRepeatable(__ex);
            }
        }
        finally
        {
	    __connection.reclaimOutgoing(__og);
        }
    }

    public String
    ice_id(java.util.Map __context)
        throws IceInternal.NonRepeatable
    {
	IceInternal.Outgoing __og = __connection.getOutgoing(__reference, "ice_id", OperationMode.Nonmutating,
							     __context, __compress);
        try
        {
            IceInternal.BasicStream __is = __og.is();
            if(!__og.invoke())
            {
                throw new UnknownUserException();
            }
            try
            {
                return __is.readString();
            }
            catch(LocalException __ex)
            {
                throw new IceInternal.NonRepeatable(__ex);
            }
        }
        finally
        {
	    __connection.reclaimOutgoing(__og);
        }
    }

    public boolean
    ice_invoke(String operation, OperationMode mode, byte[] inParams, ByteSeqHolder outParams, java.util.Map __context)
        throws IceInternal.NonRepeatable
    {
	IceInternal.Outgoing __og = __connection.getOutgoing(__reference, operation, mode, __context, __compress);
        try
        {
            if(inParams != null)
            {
                IceInternal.BasicStream __os = __og.os();
                __os.writeBlob(inParams);
            }
            boolean ok = __og.invoke();
            if(__reference.getMode() == IceInternal.Reference.ModeTwoway)
            {
                try
                {
                    IceInternal.BasicStream __is = __og.is();
                    int sz = __is.getReadEncapsSize();
                    if(outParams != null)
                    {
                        outParams.value = __is.readBlob(sz);
                    }
                }
                catch(LocalException __ex)
                {
                    throw new IceInternal.NonRepeatable(__ex);
                }
            }
            return ok;
        }
        finally
        {
	    __connection.reclaimOutgoing(__og);
        }
    }

    public Connection
    ice_connection()
    {
	return __connection;
    }

    //
    // Only for use by ObjectPrx
    //
    final void
    __copyFrom(_ObjectDelM from)
    {
        //
        // No need to synchronize "from", as the delegate is immutable
        // after creation.
        //

        //
        // No need to synchronize, as this operation is only called
        // upon initialization.
        //

	assert(__reference == null);
	assert(__connection == null);

        __reference = from.__reference;
        __connection = from.__connection;
        __compress = from.__compress;
    }

    protected IceInternal.Reference __reference;
    protected ConnectionI __connection;
    protected boolean __compress;

    public void
    setup(IceInternal.Reference ref)
    {
        //
        // No need to synchronize, as this operation is only called
        // upon initialization.
        //

	assert(__reference == null);
	assert(__connection == null);

	__reference = ref;
	BooleanHolder compress = new BooleanHolder();
	__connection = __reference.getConnection(compress);
        __compress = compress.value;
    }
}
