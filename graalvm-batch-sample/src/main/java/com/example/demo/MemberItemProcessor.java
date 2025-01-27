package com.example.demo;

import org.springframework.batch.item.ItemProcessor;

public class MemberItemProcessor implements ItemProcessor<Member, FullNameMember> {

	@Override
	public FullNameMember process(Member item) throws Exception {
		String fullName = item.getFirstName() + " " + item.getLastName();

		FullNameMember fullNameMember = new FullNameMember();
		fullNameMember.setId(item.getId());
		fullNameMember.setFirstName(item.getFirstName());
		fullNameMember.setLastName(item.getLastName());
		fullNameMember.setFullName(fullName);

		return fullNameMember;
	}

}
