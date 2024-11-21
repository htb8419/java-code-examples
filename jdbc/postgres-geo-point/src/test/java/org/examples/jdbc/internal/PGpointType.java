package org.examples.jdbc.internal;


import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.postgresql.geometric.PGpoint;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class PGpointType implements UserType<PGpoint> {

    /**
     * Return the SQL type codes for the columns mapped by this type.
     *
     * @return int[]
     */
    @Override
    public int getSqlType() {
        return Types.VARCHAR;
    }

    /**
     * The class returned by nullSafeGet().
     *
     * @return Class
     */
    @Override
    public Class<PGpoint> returnedClass() {
        return PGpoint.class;
    }

    @Override
    public boolean equals(PGpoint o, PGpoint o1) throws HibernateException {
        return ObjectUtils.nullSafeEquals(o, o1);
    }

    @Override
    public int hashCode(PGpoint o) throws HibernateException {
        return ObjectUtils.nullSafeHashCode(o);
    }

    /**
     * Retrieve an instance of the mapped class from a JDBC resultset.
     */
    @Override
    public PGpoint nullSafeGet(ResultSet resultSet, int position, SharedSessionContractImplementor session, @Deprecated Object owner) throws SQLException {
        Object object = resultSet.getObject(position);
        if (object == null) {
            return null;
        }
        return new PGpoint(object.toString());
    }

    /**
     * Write an instance of the mapped class to a prepared statement.
     */
    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, PGpoint o, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (o == null) {
            preparedStatement.setNull(i, Types.OTHER);
        } else {
            preparedStatement.setObject(i, o.toString(), Types.OTHER);
        }
    }

    @Override
    public PGpoint deepCopy(PGpoint point) throws HibernateException {
        return new PGpoint(point.x,point.y);
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(PGpoint o) throws HibernateException {
        return o;
    }

    @Override
    public PGpoint assemble(Serializable cached, Object owner) {
        return (PGpoint) cached;
    }

}