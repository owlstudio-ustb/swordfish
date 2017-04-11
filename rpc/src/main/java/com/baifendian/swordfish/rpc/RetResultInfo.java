/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.baifendian.swordfish.rpc;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 返回结果信息，返回包括执行id信息
 */
public class RetResultInfo implements org.apache.thrift.TBase<RetResultInfo, RetResultInfo._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("RetResultInfo");

  private static final org.apache.thrift.protocol.TField RET_INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("retInfo", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField EXEC_IDS_FIELD_DESC = new org.apache.thrift.protocol.TField("execIds", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new RetResultInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new RetResultInfoTupleSchemeFactory());
  }

  /**
   * 返回状态
   */
  public RetInfo retInfo; // required
  /**
   * 返回flow exec Id
   */
  public List<Integer> execIds; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 返回状态
     */
    RET_INFO((short)1, "retInfo"),
    /**
     * 返回flow exec Id
     */
    EXEC_IDS((short)2, "execIds");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // RET_INFO
          return RET_INFO;
        case 2: // EXEC_IDS
          return EXEC_IDS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.RET_INFO, new org.apache.thrift.meta_data.FieldMetaData("retInfo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, RetInfo.class)));
    tmpMap.put(_Fields.EXEC_IDS, new org.apache.thrift.meta_data.FieldMetaData("execIds", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(RetResultInfo.class, metaDataMap);
  }

  public RetResultInfo() {
  }

  public RetResultInfo(
    RetInfo retInfo,
    List<Integer> execIds)
  {
    this();
    this.retInfo = retInfo;
    this.execIds = execIds;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public RetResultInfo(RetResultInfo other) {
    if (other.isSetRetInfo()) {
      this.retInfo = new RetInfo(other.retInfo);
    }
    if (other.isSetExecIds()) {
      List<Integer> __this__execIds = new ArrayList<Integer>();
      for (Integer other_element : other.execIds) {
        __this__execIds.add(other_element);
      }
      this.execIds = __this__execIds;
    }
  }

  public RetResultInfo deepCopy() {
    return new RetResultInfo(this);
  }

  @Override
  public void clear() {
    this.retInfo = null;
    this.execIds = null;
  }

  /**
   * 返回状态
   */
  public RetInfo getRetInfo() {
    return this.retInfo;
  }

  /**
   * 返回状态
   */
  public RetResultInfo setRetInfo(RetInfo retInfo) {
    this.retInfo = retInfo;
    return this;
  }

  public void unsetRetInfo() {
    this.retInfo = null;
  }

  /** Returns true if field retInfo is set (has been assigned a value) and false otherwise */
  public boolean isSetRetInfo() {
    return this.retInfo != null;
  }

  public void setRetInfoIsSet(boolean value) {
    if (!value) {
      this.retInfo = null;
    }
  }

  public int getExecIdsSize() {
    return (this.execIds == null) ? 0 : this.execIds.size();
  }

  public java.util.Iterator<Integer> getExecIdsIterator() {
    return (this.execIds == null) ? null : this.execIds.iterator();
  }

  public void addToExecIds(int elem) {
    if (this.execIds == null) {
      this.execIds = new ArrayList<Integer>();
    }
    this.execIds.add(elem);
  }

  /**
   * 返回flow exec Id
   */
  public List<Integer> getExecIds() {
    return this.execIds;
  }

  /**
   * 返回flow exec Id
   */
  public RetResultInfo setExecIds(List<Integer> execIds) {
    this.execIds = execIds;
    return this;
  }

  public void unsetExecIds() {
    this.execIds = null;
  }

  /** Returns true if field execIds is set (has been assigned a value) and false otherwise */
  public boolean isSetExecIds() {
    return this.execIds != null;
  }

  public void setExecIdsIsSet(boolean value) {
    if (!value) {
      this.execIds = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case RET_INFO:
      if (value == null) {
        unsetRetInfo();
      } else {
        setRetInfo((RetInfo)value);
      }
      break;

    case EXEC_IDS:
      if (value == null) {
        unsetExecIds();
      } else {
        setExecIds((List<Integer>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RET_INFO:
      return getRetInfo();

    case EXEC_IDS:
      return getExecIds();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case RET_INFO:
      return isSetRetInfo();
    case EXEC_IDS:
      return isSetExecIds();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof RetResultInfo)
      return this.equals((RetResultInfo)that);
    return false;
  }

  public boolean equals(RetResultInfo that) {
    if (that == null)
      return false;

    boolean this_present_retInfo = true && this.isSetRetInfo();
    boolean that_present_retInfo = true && that.isSetRetInfo();
    if (this_present_retInfo || that_present_retInfo) {
      if (!(this_present_retInfo && that_present_retInfo))
        return false;
      if (!this.retInfo.equals(that.retInfo))
        return false;
    }

    boolean this_present_execIds = true && this.isSetExecIds();
    boolean that_present_execIds = true && that.isSetExecIds();
    if (this_present_execIds || that_present_execIds) {
      if (!(this_present_execIds && that_present_execIds))
        return false;
      if (!this.execIds.equals(that.execIds))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(RetResultInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    RetResultInfo typedOther = (RetResultInfo)other;

    lastComparison = Boolean.valueOf(isSetRetInfo()).compareTo(typedOther.isSetRetInfo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRetInfo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.retInfo, typedOther.retInfo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetExecIds()).compareTo(typedOther.isSetExecIds());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetExecIds()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.execIds, typedOther.execIds);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("RetResultInfo(");
    boolean first = true;

    sb.append("retInfo:");
    if (this.retInfo == null) {
      sb.append("null");
    } else {
      sb.append(this.retInfo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("execIds:");
    if (this.execIds == null) {
      sb.append("null");
    } else {
      sb.append(this.execIds);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (retInfo != null) {
      retInfo.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class RetResultInfoStandardSchemeFactory implements SchemeFactory {
    public RetResultInfoStandardScheme getScheme() {
      return new RetResultInfoStandardScheme();
    }
  }

  private static class RetResultInfoStandardScheme extends StandardScheme<RetResultInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, RetResultInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // RET_INFO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.retInfo = new RetInfo();
              struct.retInfo.read(iprot);
              struct.setRetInfoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // EXEC_IDS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.execIds = new ArrayList<Integer>(_list0.size);
                for (int _i1 = 0; _i1 < _list0.size; ++_i1)
                {
                  int _elem2; // required
                  _elem2 = iprot.readI32();
                  struct.execIds.add(_elem2);
                }
                iprot.readListEnd();
              }
              struct.setExecIdsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, RetResultInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.retInfo != null) {
        oprot.writeFieldBegin(RET_INFO_FIELD_DESC);
        struct.retInfo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.execIds != null) {
        oprot.writeFieldBegin(EXEC_IDS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, struct.execIds.size()));
          for (int _iter3 : struct.execIds)
          {
            oprot.writeI32(_iter3);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class RetResultInfoTupleSchemeFactory implements SchemeFactory {
    public RetResultInfoTupleScheme getScheme() {
      return new RetResultInfoTupleScheme();
    }
  }

  private static class RetResultInfoTupleScheme extends TupleScheme<RetResultInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, RetResultInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetRetInfo()) {
        optionals.set(0);
      }
      if (struct.isSetExecIds()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetRetInfo()) {
        struct.retInfo.write(oprot);
      }
      if (struct.isSetExecIds()) {
        {
          oprot.writeI32(struct.execIds.size());
          for (int _iter4 : struct.execIds)
          {
            oprot.writeI32(_iter4);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, RetResultInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.retInfo = new RetInfo();
        struct.retInfo.read(iprot);
        struct.setRetInfoIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, iprot.readI32());
          struct.execIds = new ArrayList<Integer>(_list5.size);
          for (int _i6 = 0; _i6 < _list5.size; ++_i6)
          {
            int _elem7; // required
            _elem7 = iprot.readI32();
            struct.execIds.add(_elem7);
          }
        }
        struct.setExecIdsIsSet(true);
      }
    }
  }

}
