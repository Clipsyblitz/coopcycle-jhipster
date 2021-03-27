import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICommercant, Commercant } from 'app/shared/model/commercant.model';
import { CommercantService } from './commercant.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-commercant-update',
  templateUrl: './commercant-update.component.html',
})
export class CommercantUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    companyName: [null, [Validators.required]],
    address: [null, [Validators.required]],
    phone: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(10), Validators.pattern('[0-9]*')]],
    userId: [],
  });

  constructor(
    protected commercantService: CommercantService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commercant }) => {
      this.updateForm(commercant);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(commercant: ICommercant): void {
    this.editForm.patchValue({
      id: commercant.id,
      companyName: commercant.companyName,
      address: commercant.address,
      phone: commercant.phone,
      userId: commercant.userId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commercant = this.createFromForm();
    if (commercant.id !== undefined) {
      this.subscribeToSaveResponse(this.commercantService.update(commercant));
    } else {
      this.subscribeToSaveResponse(this.commercantService.create(commercant));
    }
  }

  private createFromForm(): ICommercant {
    return {
      ...new Commercant(),
      id: this.editForm.get(['id'])!.value,
      companyName: this.editForm.get(['companyName'])!.value,
      address: this.editForm.get(['address'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      userId: this.editForm.get(['userId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommercant>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
