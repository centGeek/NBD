package shop.orm.repository.mapper;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.DaoTable;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import shop.orm.repository.dao.AddressDao;
import shop.orm.repository.dao.ClientDao;

@Mapper
public interface ClientMapper {

    @DaoFactory
    ClientDao clientDao(@DaoKeyspace String keyspace, @DaoTable String table);


    @DaoFactory
    ClientDao clientDao();
}
