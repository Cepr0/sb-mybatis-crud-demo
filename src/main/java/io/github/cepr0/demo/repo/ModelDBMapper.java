package io.github.cepr0.demo.repo;

import io.github.cepr0.demo.model.Model;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.data.domain.Pageable;

import java.util.List;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
@Mapper
public interface ModelDBMapper {

	@SelectKey(
			statement = "call next value for global_seq",
			keyProperty = "id",
			resultType = Integer.class,
			before = true
	)
	@Insert("insert into models(id, version, name) values(#{id}, #{version}, #{name})")
	int insert(Model model);

	@Select("select version from models where id = #{id}")
	Integer getVersion(int id);

	@UpdateProvider(type = SQLBuilder.class)
	int update(int id, int version, Model source);

	@Delete("delete from models where id = #{id} and version = #{version}")
	int delete(int id, int version);

	@Select("select * from models where id = #{id}")
	Model get(int id);

	@Select("select * from models")
	List<Model> getAll();

	@Select("select * from models limit #{pageSize} offset #{offset}")
	List<Model> getAllPaged(Pageable pageable);

	@Select("select count(*) from models")
	int getTotal();

	class SQLBuilder implements ProviderMethodResolver {
		public static String update(final int id, final int version, final Model source) {
			return new SQL() {{
				UPDATE("models");
				if (source.getName() != null) SET("name = #{source.name}");
				SET("version = #{version} + 1");
				WHERE("id = #{id} and version = #{version}");
			}}.toString();
		}
	}
}
