package wang.tiven.trycf.web.assembler;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import wang.tiven.trycf.model.Hero;
import wang.tiven.trycf.web.HeroController;
import wang.tiven.trycf.web.entity.HeroResource;

public class HeroAssembler extends ResourceAssemblerSupport<Hero, HeroResource> {

	public HeroAssembler(Class<HeroController> controllerClass, Class<HeroResource> resourceType) {
		super(controllerClass, resourceType);
	}

	@Override
	public HeroResource toResource(Hero entity) {
		HeroResource resource = createResourceWithId(entity.getId(), entity);
		BeanUtils.copyProperties(entity, resource);
		return resource;
	}
}
